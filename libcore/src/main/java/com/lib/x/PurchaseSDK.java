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

	public void notifyPurchaseCancel(){
		PurchaseSDK.didPruchaseCancel();
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

	public void setPayId(String payId)
	{
		if(null != payId)
			mPayId = payId;
	}

	public void setGameUserServer(String gameUserServer)
	{
		if(null != gameUserServer)
			mGameUserServer = gameUserServer;
	}

	public void setName(String name)
	{
		if(null != name)
			mName = name;
	}

	public void setGameUserId(String userId)
	{
		if(null != userId)
			mGameUserId = userId;
	}

	public void setGameUserName(String userName)
	{
		if(null != userName)
			mGameUserName = userName;
	}

	public void setPayUrl(String payUrl)
	{
		if(null != payUrl)
			mPayUrl = payUrl;
	}

	public void setProductType(String szProductType)
	{
		if(null != szProductType)
			mProductType = szProductType;
	}

	public void setProductId(String productId){
		if(null != productId)
			mProductId = productId;
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

	public static native void didPruchaseCancel();

	protected String mOrderSerial = "";

	protected float  mPrice;

	protected String mPayId = "";

	protected String mGameUserServer = "";

	protected String mName = "";

	protected String mGameUserId = "";

	protected String mGameUserName = "";

	protected String mPayUrl = "";

	protected String mProductType = "";

	protected String mProductId = "";

	private final static int StartPurchaseMsg = 1;
}
