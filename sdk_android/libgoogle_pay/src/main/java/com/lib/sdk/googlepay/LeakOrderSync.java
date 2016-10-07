package com.lib.sdk.googlepay;
import java.util.*;
import android.app.Activity;
import android.util.Log;
import com.lib.sdk.googlepay.util.*;

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

	public void start(){
		if(!PayUtil.haveGooglePlay(mActivity, PayUtil.GooglePlayPackageName)){
			if(null != mCallback)
				mCallback.didComlection(this, "Not installed GooglePlay");
		}
		else{
			this.initIab();
		}
	}

	protected void initIab(){
        mSynStorage.init();
        mHelper = new IabHelper(mActivity, mGooglePayKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
				LeakOrderSync.this.onIabSetupFinished(result);
            }
        });
	}

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

	protected void queryInventoryAsync(){
		mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener(){
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				LeakOrderSync.this.onQueryInventoryFinished(result, inv);
			}
		});
	}

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

	protected void verifyNextDeveloperOrder(){
		if(null == mListOrder || mListOrder.isEmpty() || mOrderIndex >= mListOrder.size()){
            mListOrder = null;
			mOrderIndex = 0;
			//如果没有需要同步的订单那么就表示没有掉单的
			if(null != mCallback)
				mCallback.didComlection(this, null);
		}
		else{
			this.log("verifyNextDeveloperPayload verifyDeveloperPayload " + mOrderIndex);
			String orderId = mListOrder.get(mOrderIndex);
            SynStorage.Item item =   mSynStorage.get(orderId);
			this.verifyDeveloperSelfOrder(item);
		}
	}

	protected void verifyDeveloperSelfOrder(SynStorage.Item item) {
		//使用自定义的请求去和自己的服务器同步一下，自定义订单是否有效
		SynRequest request = SyncRequestFactory.createSynRequest(item.purchase, new SynRequest.Callback(){
			@Override
			public void didComlection(SynRequest request,  int errorCode, String error){
				if(null == error){
					//没有问题，那么就开始消费google的支付
					LeakOrderSync.this.asynConsume(request.getPurchase());
				}
				else{
					//todo 这边也待定，主要是要进行下一个同步，还是直接跳过
				}
			}
		});
		request.startVerifyOrder();
	}

	protected void asynConsume(Purchase purchase){
		//根据商品类型进行验证处理，通知服务器购买完成
		if(purchase.getSku().indexOf("NCS") != 0){  //目前而言，我们游戏属于非一次性消费类
			mHelper.consumeAsync(purchase,  new IabHelper.OnConsumeFinishedListener(){
				@Override
				public void onConsumeFinished(Purchase purchase, IabResult result) {
					LeakOrderSync.this.asynConsumeFinished(purchase, result);
				}
			});
		}
		else{//NCS开头的ID为一次消费类ID，不用消费他
			this.log("didVerifyOrder We have Purchase's not Consuming object.");
			mSynStorage.removeAndSave(purchase.getDeveloperPayload());//事务结束，清除它
			this.verifyNextDeveloperOrder();
		}
	}

	protected void asynConsumeFinished(Purchase purchase, IabResult result){

		if (result.isSuccess()) {
			SynStorage.Item item = mSynStorage.get(purchase.getDeveloperPayload());
			item.setNotSynServverState();
			mSynStorage.save();

			//通知服务端消费成功
			SynRequest request = SyncRequestFactory.createSynRequest(purchase, new SynRequest.Callback(){
				@Override
				public void didComlection(SynRequest request,  int errorCode, String error){
					if(null == error) {
						//服务端说没问题了，那么就进行下一个
						LeakOrderSync.this.mSynStorage.removeAndSave(request.getPurchase().getDeveloperPayload());
						LeakOrderSync.this.mOrderIndex++;//索引加
						LeakOrderSync.this.verifyNextDeveloperOrder();
					}
                    else{
                        //todo 如果出问题了，那么就不继续进行了
                    }
				}
			});
			request.startSyncConsumeResult();
		}
		else {
            if(null != mCallback)
                mCallback.didComlection(this, result.getMessage());
		}
	}

    /**
     * 合并从goolge play那边获取的漏单信息，合并到SynStorage中
     * @param inventory
     */
    protected void mergeWithInventory(Inventory inventory){
        List<Purchase>  purchaseList = inventory.getAllPurchases();
        int sizeOfPurchase = purchaseList == null ? 0 : purchaseList.size();
        this.log( "mergeWithInventory size " + sizeOfPurchase);
        if(sizeOfPurchase == 0)
            return;
        for(int i = 0; i < sizeOfPurchase; i++){
            Purchase purchase = purchaseList.get(i);
            String devPlayload = purchase.getDeveloperPayload();
            SynStorage.Item item = mSynStorage.get(devPlayload);
            if(null == item){
                item = new SynStorage.Item();
                item.orderSerial = purchase.getDeveloperPayload();
                mSynStorage.add(item);
            }
            item.purchase = purchase;
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

    public IabHelper getIabHelper(){
        return mHelper;
    }

	public SynStorage getSynStorage(){
		return mSynStorage;
	}

	public void onDestory() {
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

