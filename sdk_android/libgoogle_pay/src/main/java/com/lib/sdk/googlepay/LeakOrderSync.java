package com.lib.sdk.googlepay;
import java.util.*;
import android.app.Activity;
import android.util.Log;
import com.lib.sdk.googlepay.util.*;
import com.lib.x.SDKCenter;

public class LeakOrderSync {

	public interface Callback{
		public void didComlection(LeakOrderSync transcation, String error);
	}

	public LeakOrderSync(Activity activity){
		mActivity = activity;
	}

    public void setActivity(Activity activity){
        mActivity = activity;
    }

	public void setGooglePayKey(String publicKey){
		mGooglePayKey = publicKey;
	}

    public void setCallback(LeakOrderSync.Callback callback){
        mCallback = callback;
    }

    public IabHelper getIabHelper(){
        return mHelper;
    }

    public SynStorage getSynStorage(){
        return mSynStorage;
    }

	public void start(){
		if(!PayUtil.haveGooglePlay(mActivity, PayUtil.GooglePlayPackageName)){
			if(null != mCallback)
				mCallback.didComlection(this, "Not installed GooglePlay");
		}
		else{
			this.initIab();
		}
	}

    /**
     * 开始初始化in app billing
     */
    protected void initIab(){
        mSynStorage.init();
        mHelper = new IabHelper(mActivity, mGooglePayKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
				LeakOrderSync.this.onIabSetupFinished(result);
            }
        });
	}

    /**
     * 初始化完成处理，失败了就通知出去
     * @param result
     */
	protected void onIabSetupFinished(IabResult result){
		if (!result.isSuccess()) {
			//初始化不成功
			if (null != mCallback)
				mCallback.didComlection(this, result.getMessage());
			this.log("onIabSetupFinished no success "+ result.getMessage());
			return;
		}
		this.queryInventoryAsync();
	}

    /**
     * 开始异步查询是否有漏单
     */
	protected void queryInventoryAsync(){
        try {
            mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener() {
                @Override
                public void onQueryInventoryFinished(IabResult result, Inventory inv) {
                    LeakOrderSync.this.onQueryInventoryFinished(result, inv);
                }
            });
        }catch (IabHelper.IabAsyncInProgressException e){
            if(null != mCallback)
                mCallback.didComlection(this, e.getMessage());
            e.printStackTrace();
        }
	}

    /**
     * 异步漏单查询完毕处理，查询失败了就通知出去，成功那么就合并漏单项，同时开始验证漏单项
     * @param result
     * @param inventory
     */
	protected void onQueryInventoryFinished(IabResult result, Inventory inventory){
		if (result.isFailure()) {
			if(null != mCallback)
				mCallback.didComlection(this, result.getMessage());
			this.log( "onQueryInventoryFinished result isFailure " + result);
			return;
		}

		this.mergeWithInventory(inventory);
		this.verifyNextDeveloperOrder();
	}

    /**
     * 开始进行下一个订单验证
     */
	protected void verifyNextDeveloperOrder(){
		if(null == mListOrder || mListOrder.isEmpty() || mOrderIndex >= mListOrder.size()){
            mListOrder = null;
			mOrderIndex = 0;
			//如果没有需要同步的订单那么就表示没有掉单的
			if(null != mCallback)
				mCallback.didComlection(this, null);
		}
		else{
			String orderId = mListOrder.get(mOrderIndex);
            SynStorage.Item item =   mSynStorage.get(orderId);
            if(item.isNotConsume()){
                this.asynConsume(item.getPurchase());
            }
            else if(item.isNotSynServer()){//如果处于为何服务器同步状态，那么就同步
                this.log("verifyNextDeveloperOrder not synserver");
                this.startSyncRequest(item);
            }
            else{
                mOrderIndex++;
                this.verifyNextDeveloperOrder();
            }
		}
	}

    /**
     * 开始异步消费
     * @param purchase
     */
	protected void asynConsume(Purchase purchase){
		//根据商品类型进行验证处理，通知服务器购买完成
		if(purchase.getSku().indexOf("NCS") != 0){  //目前而言，我们游戏属于非一次性消费类
            try {
                mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
                    @Override
                    public void onConsumeFinished(Purchase purchase, IabResult result) {
                        LeakOrderSync.this.asynConsumeFinished(purchase, result);
                    }
                });
            }catch (IabHelper.IabAsyncInProgressException e){
                if(null != mCallback)
                    mCallback.didComlection(this, e.getMessage());
                e.printStackTrace();
            }
		}
		else{//NCS开头的ID为一次消费类ID，不用消费他
			this.log("didVerifyOrder We have Purchase's not Consuming object.");
			mSynStorage.removeAndSave(purchase.getDeveloperPayload());//事务结束，清除它
            mOrderIndex++;
			this.verifyNextDeveloperOrder();
		}
	}

	/**
	 * 异步消费完成处理函数，没出问题就开始和服务器同步，有问题就结束
	 * @param purchase
	 * @param result
     */
	protected void asynConsumeFinished(Purchase purchase, IabResult result){

		if (result.isSuccess()) {
			SynStorage.Item item = mSynStorage.get(purchase.getDeveloperPayload());
			item.setNotSynServverState();
			mSynStorage.save();
            this.startSyncRequest(item);
		}
		else {
            //消费失败了，那么就继续消费下一单
            mOrderIndex++;
            this.verifyNextDeveloperOrder();
		}
	}

    protected void startSyncRequest(SynStorage.Item item){
        GooglePayPurchase googlePayPurchase = (GooglePayPurchase)SDKCenter.purchase();
        //通知服务端消费成功
        SynRequest request = new SynRequest(googlePayPurchase, item, new SynRequest.Callback(){
            @Override
            public void didComlection(SynRequest request, boolean isSuccess,  int errorCode, String error){
                LeakOrderSync.this.onSynRequestFinish(request, isSuccess, errorCode, error);
            }
        });
        request.startSyncConsumeResult();
    }

    protected void onSynRequestFinish(SynRequest request, boolean success, int errorCode, String error){
        this.log("onSynRequestFinish ");
        LeakOrderSync.this.mOrderIndex++;//索引加
        if(success) {
            //服务端说没问题了，那么就进行下一个
            this.log("onSynRequestFinish successx");
            LeakOrderSync.this.mSynStorage.removeAndSave(request.getItem().orderSerial);
        }
        //不管有没问题，都验证下一单
        LeakOrderSync.this.verifyNextDeveloperOrder();

    }

    /**
     * 合并从goolge play那边获取的漏单信息，合并到SynStorage中
     * @param inventory
     */
    protected void mergeWithInventory(Inventory inventory){
        List<Purchase>  purchaseList = inventory.getAllPurchases();
        int sizeOfPurchase = purchaseList == null ? 0 : purchaseList.size();
        this.log( "mergeWithInventory size " + sizeOfPurchase);
        if(sizeOfPurchase == 0) {
            Set<String> keys = mSynStorage.getKeys();
            if(null != keys)
                mListOrder = new ArrayList<String>(keys);
            mOrderIndex = 0;
            return;
        }

        for(int i = 0; i < sizeOfPurchase; i++){
            Purchase purchase = purchaseList.get(i);
            String devPlayload = purchase.getDeveloperPayload();
            SynStorage.Item item = mSynStorage.get(devPlayload);
            if(null == item){
                item = new SynStorage.Item();
                item.orderSerial = purchase.getDeveloperPayload();
                mSynStorage.add(item);
            }
            item.setPurchase(purchase);
            //不管之前状态如何，都设置为未消费状态
            item.setNotConsumeState();
            mSynStorage.add(item);

        }
        Set<String> keys = mSynStorage.getKeys();
        if(null != keys)
            mListOrder = new ArrayList<String>(keys);
        else
            mListOrder = null;
        mOrderIndex = 0;
        mSynStorage.save();
    }



	private void log(String log){
		Log.d("GooglePayPurchase", "GooglePayPurchase " + log);
	}

	protected Callback mCallback;
	protected Activity mActivity;
	protected IabHelper mHelper;
	protected String 	mGooglePayKey;
    protected ArrayList<String> mListOrder;
	protected int mOrderIndex = 0;
	protected SynStorage	mSynStorage = new SynStorage();
}

