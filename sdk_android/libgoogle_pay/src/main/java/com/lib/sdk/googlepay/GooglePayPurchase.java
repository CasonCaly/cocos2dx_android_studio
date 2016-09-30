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
            mLeakOrderSync.start();
        }
        else {
            mLeakOrderSync.setActivity(this.getGameActivity());
        }
        mHelper = mLeakOrderSync.getIabHelper();
    }

    @Override
    public void startPurchase() {
        if(mLeakOrderSyncFinish) {
            Activity act = this.getGameActivity();
            String cpOrder = GooglePayPurchase.getCpOrder(mGameUserId, mGameUserServer, 204, "appfame_jp");
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
        if (null == purchase){
            this.notifyFailedWithStepAndDescp(GooglePayPurchase.PurchaseWithPurchaseNullError, null);
            Log.d(TAG, "onIabPurchaseFinished: purchase is null");
            return;
        }
        if (result.isFailure()){
            if (IabHelper.IABHELPER_USER_CANCELLED != result.getResponse()){
                this.notifyFailedWithStepAndDescp(GooglePayPurchase.PurchaseWithFail, result.toString());
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
        if (null == mHelper) {
            return;
        }

        mHelper.consumeAsync(purchase, new IabHelper.OnConsumeFinishedListener(){
                public void onConsumeFinished(Purchase purchase, IabResult result){
                    if (null == mHelper){
                        return;
                    }
                    if (result.isSuccess()){
                        GooglePayPurchase.this.onConsumeFinished(purchase);
                    }
                }
            }
        );
    }

    protected void onConsumeFinished(Purchase purchase){

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

    protected void notifyFailedWithStepAndDescp(int step, String error){
        String descp = "Payment failed, error step is " + step;
        if(null != error && !error.isEmpty())
            descp += ", error description is "+error;
        this.notifyPurchaseFinish(descp);
    }

    protected final String TAG = "IabStore";
    private int mReqId = 10000;
    private boolean mHavePurchaseBeforeLeakOrderSync = false;
    private boolean mLeakOrderSyncFinish = false;
    protected IabHelper mHelper;
    protected String mGooglePayKey;
    protected boolean mDebugLog = false;
    protected LeakOrderSync mLeakOrderSync;

    //以下是步骤码
    public final static int PurchaseWithPurchaseNullError = 400;//购买时遇到Purchase对象为空错误
    public final static int PurchaseWithFail = 401;//购买失败
}
