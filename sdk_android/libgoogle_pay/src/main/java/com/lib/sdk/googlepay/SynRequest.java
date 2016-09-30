package com.lib.sdk.googlepay;

import com.lib.sdk.googlepay.util.Purchase;

/**
 * Created by Administrator on 2016/9/26 0026.
 */
public abstract class SynRequest {

    public interface Callback{
        public void didComlection(SynRequest request, int errorCode, String error);
    }

    public SynRequest(Callback callback, Purchase purchase){
        mCallback = callback;
        mPurchase = purchase;
    }

    public abstract void startVerifyOrder();

    public abstract void startSyncConsumeResult();

    public Purchase getPurchase(){
        return mPurchase;
    }

    protected Callback mCallback;
    protected Purchase mPurchase;
}
