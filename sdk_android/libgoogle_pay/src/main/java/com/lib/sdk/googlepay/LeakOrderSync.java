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
        mHelper = new IabHelper(mActivity, mPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
				LeakOrderSync.this.onIabSetupFinished(result);
            }
        });
	}

	protected void onIabSetupFinished(IabResult result){
		this.log( "onIabSetupFinished");
		if (!result.isSuccess()) {
			//初始化不成功
			if (null != mCallback)
				mCallback.didComlection(this, result.getMessage());
			this.log("onIabSetupFinished no success");
			return;
		}
		this.queryInventoryAsync();
	}

	protected void queryInventoryAsync(){
		this.log("queryInventoryAsync");
		mHelper.queryInventoryAsync(new IabHelper.QueryInventoryFinishedListener(){
			@Override
			public void onQueryInventoryFinished(IabResult result, Inventory inv) {
				LeakOrderSync.this.onQueryInventoryFinished(result, inv);
			}
		});
	}

	protected void onQueryInventoryFinished(IabResult result, Inventory inventory){
		// Is it a failure?
		if (result.isFailure()) {
			if(null != mCallback)
				mCallback.didComlection(this, result.getMessage());
			this.log( "onQueryInventoryFinished result isFailure " + result);
			return;
		}

		mListPurchase = inventory.getAllPurchases();
		int sizeOfPurchase = mListPurchase == null ? 0 : mListPurchase.size();
		this.log( "onQueryInventoryFinished succcess size " + sizeOfPurchase);
		this.verifyNextDeveloperOrder();
	}

	protected void verifyNextDeveloperOrder(){
		if(null == mListPurchase || mListPurchase.isEmpty() || mPurchaseIndex >= mListPurchase.size()){
			mListPurchase = null;
			mPurchaseIndex = 0;
			//如果没有需要同步的订单那么就表示没有掉单的
			if(null != mCallback)
				mCallback.didComlection(this, null);
		}
		else{
			this.log("verifyNextDeveloperPayload verifyDeveloperPayload " + mPurchaseIndex);
			Purchase purchase = mListPurchase.get(mPurchaseIndex);
			this.verifyDeveloperSelfOrder(purchase);
		}
	}

	protected void verifyDeveloperSelfOrder(Purchase purchase) {
		this.log("verifyDeveloperPayload " + purchase.getDeveloperPayload());
		//使用自定义的请求去和自己的服务器同步一下，自定义订单是否有效
		SynRequest request = SyncRequestFactory.createSynRequest(purchase, new SynRequest.Callback(){
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
			this.log("We have Purchase's object. Consuming it.");
			mHelper.consumeAsync(purchase,  new IabHelper.OnConsumeFinishedListener(){
				@Override
				public void onConsumeFinished(Purchase purchase, IabResult result) {
					LeakOrderSync.this.asynConsumeFinished(purchase, result);
				}
			});
		}
		else{//NCS开头的ID为一次消费类ID，不用消费他
			this.log("didVerifyOrder We have Purchase's not Consuming object.");
			mSynStorage.remove(purchase.getSku());//事务结束，清除它
			mSynStorage.save();
			this.verifyNextDeveloperOrder();
		}
	}

	protected void asynConsumeFinished(Purchase purchase, IabResult result){
		if(null == purchase)
			return;

		if (result.isSuccess()) {
			SynStorage.Item item = mSynStorage.getItem(purchase.getSku());
			item.setState(SynStorage.NotSynServer);
			mSynStorage.save();

			this.log("onConsumeFinished Consumption successful. Provisioning.");
			//通知服务端消费成功
			SynRequest request = SyncRequestFactory.createSynRequest(purchase, new SynRequest.Callback(){
				@Override
				public void didComlection(SynRequest request,  int errorCode, String error){
					if(null == error) {
						//服务端说没问题了，那么就进行下一个
						LeakOrderSync.this.mSynStorage.remove(request.getPurchase().getSku());
						LeakOrderSync.this.mSynStorage.save();
						LeakOrderSync.this.mPurchaseIndex++;//索引加
						LeakOrderSync.this.verifyNextDeveloperOrder();
					}
				}
			});
			request.startSyncConsumeResult();
		}
		else {
			//todo 这边待定，可能是已经消费过的错误，或者网络错误之类的
		}
	}

    public IabHelper getIabHelper(){
        return mHelper;
    }

	public void onDestory() {
		mSynStorage.save();
	}

	private void log(String log){
		Log.d("IabTransaction", "IabTransaction " + log);
	}

	protected Callback mCallback;
	protected Activity mActivity;
	protected IabHelper mHelper;
	protected String mPublicKey;
	protected List<Purchase> mListPurchase;
	protected int 			 mPurchaseIndex = 0;
	protected SynStorage	mSynStorage = new SynStorage();
}

