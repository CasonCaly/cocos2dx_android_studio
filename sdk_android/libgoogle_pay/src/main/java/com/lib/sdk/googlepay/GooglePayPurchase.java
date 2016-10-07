package com.lib.sdk.googlepay;
import android.app.Activity;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import com.lib.sdk.googlepay.util.*;
import com.lib.x.PurchaseSDK;
import java.text.SimpleDateFormat;

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
            String cpOrder = GooglePayPurchase.getCpOrder(mGameUserId, mGameUserServer, 204, "appfame_jp");
            this.setOrderSerial(cpOrder);
            mHelper.launchPurchaseFlow(act, mProductId, mReqId, new IabHelper.OnIabPurchaseFinishedListener() {
                @Override
                public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
                    GooglePayPurchase.this.onIabPurchaseFinished(result, purchase);
                }
            }, cpOrder);
            mReqId++;
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
            mHelper.dispose();
            mHelper = null;
        }
    }


    protected void onIabPurchaseFinished(IabResult result, Purchase purchase){
        if (result.isFailure()){
            if (IabHelper.IABHELPER_USER_CANCELLED != result.getResponse()){
                this.notifyPurchaseFinish(result.getMessage());
                this.log("onIabPurchaseFinished 95");
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
        this.log("asyncConsume purchase signature "+ purchase.getSignature());
        this.log("asyncConsume purchase " + purchase.getOriginalJson());
        //消费之前先记录下来
        SynStorage.Item item = new SynStorage.Item();
        item.setNotConsumeState();
        item.gameUserId = this.mGameUserId;
        item.gameUserServer = this.mGameUserServer;
        item.orderSerial = purchase.getDeveloperPayload();
        item.price = this.mPrice;
        mLeakOrderSync.getSynStorage().addAndSave(item);

        mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener(){
                public void onConsumeFinished(Purchase purchase, IabResult result){
                    if (result.isSuccess()){
                        GooglePayPurchase.this.onConsumeFinished(purchase);
                    }
                    else{
                        GooglePayPurchase.this.notifyPurchaseFinish(result.getMessage());
                        GooglePayPurchase.this.log("asyncConsume fail " + result.getMessage());
                    }
                }
            }
        );
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
    }

    public static String getCpOrder(String _accountId, String _serverId, int _channelId, String _channelFlag) {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd-hh-mm-ss");
        final String date = sDateFormat.format(new java.util.Date());
        int random = (int)(Math.random() * (9999 - 1000)) + 1000;//四位随机数字
        String appOrderId = String.format("%s--%s--%s--%s--%s--%s",
                _accountId,
                _serverId,
                ""+_channelId,
                date,
                _channelFlag,
                ""+random);
        return appOrderId;
    }

    private void log(String log){
        Log.d("GooglePayPurchase", "GooglePayPurchase " + log);
    }

    protected final String TAG = "IabStore";
    private int mReqId = 10000;
    private boolean mHavePurchaseBeforeLeakOrderSync = false;
    private boolean mLeakOrderSyncFinish = false;
    protected IabHelper mHelper;
    protected String mGooglePayKey;
    protected boolean mDebugLog = false;
    protected LeakOrderSync mLeakOrderSync;

}
