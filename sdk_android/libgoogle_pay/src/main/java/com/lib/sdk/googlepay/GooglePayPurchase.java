package com.lib.sdk.googlepay;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import com.lib.sdk.googlepay.util.*;
import com.lib.x.PurchaseSDK;

public class GooglePayPurchase extends PurchaseSDK {

    public GooglePayPurchase() {
        super();
    }

    public void setGooglePayKey(String publicKey) {
        mGooglePayKey = publicKey;
    }

    public void enableDebugLog(boolean able) {
        mDebugLog = able;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        if(null == mLeakOrderSync){
            mLeakOrderSync = new LeakOrderSync(this.getGameActivity());
            mLeakOrderSync.setGooglePayKey(mGooglePayKey);
            mLeakOrderSync.setCallback(new LeakOrderSync.Callback(){
                    @Override
                    public void didComlection(LeakOrderSync transcation, String error){
                        mLeakOrderSyncFinish = true;
                        if(mHavePurchaseBeforeLeakOrderSync){
                            startPurchase();
                        }
                    }
                }
            );

        }
        else {
            mLeakOrderSync.setActivity(this.getGameActivity());
        }

    }

    @Override
    public void prepare(){
        mLeakOrderSync.start();
    }

    @Override
    public void startPurchase() {
        if(mLeakOrderSyncFinish) {
            mHelper = mLeakOrderSync.getIabHelper();
            Activity act = this.getGameActivity();
            String cpOrder = PayUtil.getDevOrder(this);
            this.setOrderSerial(cpOrder);
            try {
                mHelper.launchPurchaseFlow(act, mProductId, mReqId, new IabHelper.OnIabPurchaseFinishedListener() {
                    @Override
                    public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                        GooglePayPurchase.this.onIabPurchaseFinished(result, purchase);
                    }
                }, cpOrder);
                mReqId++;
            }
            catch (IabHelper.IabAsyncInProgressException e) {
                this.notifyPurchaseFinish(e.getLocalizedMessage());
                e.printStackTrace();
            }
        }else{
            mHavePurchaseBeforeLeakOrderSync = true;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(mHelper != null) {
           mHelper.handleActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onDestroy() {
        if (null != mHelper){
            try {
                mHelper.dispose();
                mHelper = null;
            }catch (IabHelper.IabAsyncInProgressException e){
                e.printStackTrace();
            }
        }
    }


    protected void onIabPurchaseFinished(IabResult result, Purchase purchase){
        if (result.isFailure()){
            if (IabHelper.IABHELPER_USER_CANCELLED != result.getResponse()){
                this.notifyPurchaseFinish(result.getMessage());
            }
            else {
                this.notifyPurchaseCancel();
            }
        }
        else{
            //开始消费订单
            this.asyncConsume(purchase);
        }
    }

    protected void asyncConsume(Purchase purchase)  {
        //消费之前先记录下来
        SynStorage.Item item = new SynStorage.Item();
        item.setNotConsumeState();
        item.gameUserId = this.mGameUserId;
        item.gameUserServer = this.mGameUserServer;
        item.orderSerial = purchase.getDeveloperPayload();
        item.price = this.mPrice;
        item.setPurchase(purchase);

        mLeakOrderSync.getSynStorage().addAndSave(item);
        try {
            mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener() {
                        public void onConsumeFinished(Purchase purchase, IabResult result) {
                            if (result.isSuccess()) {
                                GooglePayPurchase.this.onConsumeFinished(purchase);
                            } else {
                                GooglePayPurchase.this.notifyPurchaseFinish(result.getMessage());
                                GooglePayPurchase.this.log("asyncConsume fail " + result.getMessage());
                            }
                        }
                    }
            );
        }catch (IabHelper.IabAsyncInProgressException e){
            e.printStackTrace();
        }
    }

    protected void onConsumeFinished(Purchase purchase){
        //设置为未于服务器同步状态，并存储
        SynStorage synStorage = mLeakOrderSync.getSynStorage();
        SynStorage.Item item  = synStorage.get(purchase.getDeveloperPayload());
        if(null != item) {
            item.setNotSynServverState();
            synStorage.save();
        }
        //下一步开始和服务端同步
        SynRequest request = new SynRequest(this, item, new SynRequest.Callback(){
            @Override
            public void didComlection(SynRequest request, boolean success, int errorCode, String error){
                GooglePayPurchase.this.onSynRequestFinish(request, success, errorCode, error);
            }
        });
        request.startSyncConsumeResult();
    }

    protected void onSynRequestFinish(SynRequest request, boolean success, int errorCode, String error){
        if(success){
            SynStorage.Item item = request.getItem();
            SynStorage syncStorage =  mLeakOrderSync.getSynStorage();
            syncStorage.removeAndSave(item.orderSerial);
            this.notifyPurchaseFinish(null);
        }
        else{
            this.notifyPurchaseFinish(error);
        }
    }

    private void log(String log){
        Log.d("GooglePayPurchase", "GooglePayPurchase " + log);
    }

    private int mReqId = 10000;
    private boolean mHavePurchaseBeforeLeakOrderSync = false;
    private boolean mLeakOrderSyncFinish = false;
    protected IabHelper mHelper;
    protected String mGooglePayKey;
    protected boolean mDebugLog = false;
    protected LeakOrderSync mLeakOrderSync;

}
