package com.lib.x;

import android.os.Message;

/**
 * 
 * @author zhuang yusong
 *
 */
public class PurchaseSDK extends ISDK{
	
	public PurchaseSDK() {
		super();
	}

	public boolean isDefault()
	{
		String className = this.getClass().getName();
		if(className.equals(SDKCenter.DefaultPurchaseSDKName))
			return true;
		else
			return false;
	}

	public void notifyPurchaseFinish(String error){
		PurchaseSDK.didPurchaseFinish(error);
	}

	public void clear(){
		super.clear();
		mOrderSerial = "";
		mPrice = 0;
	}
	
	public void setOrderSerial(String orderSerial){
		mOrderSerial = orderSerial;
	}
	
	public String getOrderSerial(){
		return mOrderSerial;
	}
	
	public void setPrice(float price){
		mPrice = price;
	}

	public float getPrice(){
		return mPrice;
	}
	
	public void startPurchase(){
		
	}

	public void handleMessage(Message msg){
		super.handleMessage(msg);
		if(PurchaseSDK.StartPurchaseMsg == msg.what)
			this.startPurchase();
	}
	
	protected void startPurchaseInGLThread(){
		Message msg = new Message();
		msg.what = StartPurchaseMsg;
		this.sendMessage(msg);			
	}
	
	public static native void didPurchaseFinish(String error);
	
	protected String mOrderSerial = "";

	protected float  mPrice;
	
	private final static int StartPurchaseMsg = 1;
}
