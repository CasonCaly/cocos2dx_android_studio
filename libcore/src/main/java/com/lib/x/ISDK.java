package com.lib.x;

import java.util.HashMap;

import org.cocos2dx.lib.Cocos2dxActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author zhuang yusong
 *
 */
public class ISDK extends Handler{

	public ISDK()
    {
		mOtherInfo = new HashMap<String, String>();
		mFunParams = new HashMap<String, String>();
		mCocos2dxActivity = null;
	}

	public boolean isDefault()
	{
		return true;
	}


	public Activity getGameActivity(){
		SDKCenter center = SDKCenter.getInstance();
		mGameActivity = center.getGameActivity();
		return mGameActivity;
	}

	public void setGameActivity(Activity activity){
		mGameActivity = activity;
	}

//以下都是功能性函数
	/**
	 * 改函数用于用于被jni调用
	 */
	public void prepareSDK()
    {
		if(mIsPrepare)
			return;

		long mainThreadId = SDKCenter.getInstance().getMainThreadId();
		long curThreadId = Thread.currentThread().getId();
		if(mainThreadId == curThreadId)
		{
			this.prepare();
			mIsPrepare = true;
			return;
		}

		//等待主线程处理完成
//		synchronized (this) {
//			try {
		Message msg = new Message();
		msg.what = PrepareSDKMsg;
		this.sendMessage(msg);
//                this.wait();
//                mIsPrepare = true;
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		}
	}
		
	/**
	 * 处理handler的信息
	 * @param msg
	 */
    @Override
	public void handleMessage(Message msg){
		if(ISDK.CallFunctionMsg == msg.what){
			String funName = msg.getData().getString("funName");		
			this.callFunction(funName);
		}
		else if(ISDK.CallFunctionEndMsg == msg.what){
			this.callFunctionEnd();
		}
		else if(ISDK.CallFunctionBeginMsg == msg.what){
			this.callFuntionBegin();
		}
		else if(ISDK.AddFunctionParamMsg == msg.what){
			Bundle data = msg.getData();
			String key = data.getString("key");
			String value = data.getString("value");
			this.addFunctionParam(key, value);
		}
		else if(ISDK.PrepareSDKMsg == msg.what){
			this.prepare();
			synchronized (this) {
				this.notify();
			}
		}
	}
	
//下面的函数属于配置函数
	
	public void setHandler(Handler handler)
    {
		mHandler = handler;
	}
	
	/**
	 * 设置Activity，不可随便调用
	 * @param activity
	 */
	public void setCocos2dxActivity(Cocos2dxActivity activity)
    {
		mCocos2dxActivity = activity;
	}

	/**
	 * 获取Activity
	 * @return
	 */
	public Cocos2dxActivity getCocos2dxActivity()
    {
		return mCocos2dxActivity;
	}

	/**
	 * 获取由客户端设置的sdk其他信息
	 * @param key
	 * @return
	 */
	public String getOtherInfo(String key)
    {
		return mOtherInfo.get(key);
	}

	/**
	 * 设置sdk所需要的其他信息
	 * @param key
	 * @param value
	 */
	public void setOtherInfo(String key, String value)
    {
	    if (null != key && null != value)
        {
	    	mOtherInfo.remove(key);
	        mOtherInfo.put(key, value);
	    }	
	}
	
//下面的函数属于和jni调用java函数有关的函数
	
	/**
	 * 客户端(C++,Lua,JS)执行java方面的函数准备
	 */
	public void callFuntionBegin()
    {
		mFunParams.clear();
	}

	/**
	 * 客户端(C++,Lua,JS)执行java方面的函数，提交函数参数
	 * @param key
	 * @param value
	 */
	public void addFunctionParam(String key, String value)
    {
	    if(null != key && null != value)
	    	mFunParams.put(key, value);
	}

	/**
	 * 客户端(C++,Lua,JS)执行java方面的函数
	 * 这个函数可以被复写
	 * @param name
	 */
	public void callFunction(String name){}

	/**
	 * 客户端(C++,Lua,JS)执行java方面的函数结束
	 */
	public void callFunctionEnd()
    {
		mFunParams.clear();
	}
	
	protected void callFuntionBeginInGLThread()
    {
		Message msg = new Message();
		msg.what = CallFunctionBeginMsg;
		this.sendMessage(msg);
	}
	
	protected void addFunctionParamInGLThread(String key, String value)
    {
		Message msg = new Message();
		msg.what = AddFunctionParamMsg;
		Bundle data = new Bundle();
		data.putString("key", key);
		data.putString("value", value);
		this.sendMessage(msg);
	}
	
	protected void callFunctionInGLThread(String funName)
    {
		Message msg = new Message();
		msg.what = CallFunctionMsg;
		Bundle data = new Bundle();
		data.putString("funName", funName);
		this.sendMessage(msg);
	}
	
	protected void callFunctionEndInGLThread()
    {
		Message msg = new Message();
		msg.what = CallFunctionEndMsg;
		this.sendMessage(msg);
	}
	
	public void clear()
    {
		mOtherInfo.clear();
		mFunParams.clear();
	}
	
//以下的函数都可以被复写
	/**
	 * 准备特定sdk的函数
	 */
	public void prepare(){}

	//下面的函数都是和activity的有关的函数，可以被复写
	public void onResume(){}
	
	public void onPause(){}
	
	public void onDestroy(){}
	
	public void onCreate(final Bundle savedInstanceState){}
	
	public void onStart(){}
	
	public void onStop(){}
	
	public void onActivityResult(int requestCode, int resultCode, Intent data){	}

    public void onSaveInstanceState(Bundle outState){}

//以下定义的是变量
	protected Handler mHandler;
	
	protected HashMap<String, String> mOtherInfo;
	
	protected HashMap<String, String> mFunParams;
	
	protected Cocos2dxActivity mCocos2dxActivity;
	
	protected boolean mIsPrepare = false;

	protected Activity mGameActivity;

// 以下定义的是GL线程和主线程之间的消息号
	
	protected final static int CallFunctionMsg = -1;
	
	protected final static int CallFunctionEndMsg = -2;
	
	protected final static int CallFunctionBeginMsg = -3;
	
	protected final static int AddFunctionParamMsg = -4;
	
	protected final static int PrepareSDKMsg = -5;
}
