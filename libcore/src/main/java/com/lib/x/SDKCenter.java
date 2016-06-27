package com.lib.x;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * sdk的实例信息，其实是某种sdk的管理类
 * @author zhuang yusong
 */
class  SDKInstanceInfo
{
	public SDKInstanceInfo()
    {
		mMainThreadId =  Thread.currentThread().getId();
		mMapSDK = new HashMap<String, ISDK>();
	}
	
	/**
	 * 通过类名（需要完整的包名）来设置默认sdk
	 * @param defaultClassName
	 */
	public void setDefaultSDKByClassName(String defaultClassName)
    {
		ISDK sdk = mMapSDK.get(defaultClassName);
		if(null != sdk)
			mDefaultSDK = sdk;
	}
	
	public ISDK getDefaultSDK()
    {
		return mDefaultSDK;
	}
	
	public HashMap<String, ISDK> mapSDK()
    {
		return mMapSDK;
	}

    public boolean hasSDK(String className)
    {
        return null != mMapSDK.get(className);
    }
	
	public void addSDK(ISDK sdk)
    {
		if(null == sdk)
			return;
		
		String className = sdk.getClass().getName();
		ISDK targetSdk = mMapSDK.get(className);
		if(null == targetSdk)
			mMapSDK.put(className, sdk);
	}

	public void setDefaultSDK(ISDK defaultSDK)
    {
		if(null == defaultSDK)
			return;
		mDefaultSDK = defaultSDK;
		this.addSDK(defaultSDK);
	}

    public void onCreate(final Bundle saveInstanceState, boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onCreate(saveInstanceState);
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onCreate(saveInstanceState);
            }
        }
    }

    public void onResume(boolean defaultSDK)
    {
       if(defaultSDK && null != mDefaultSDK)
       {
           mDefaultSDK.onResume();
       }
       else
       {
           Set<String> allKey = mMapSDK.keySet();
           Iterator<String> iterator = allKey.iterator();
           while(iterator.hasNext())
           {
               String key = iterator.next();
               ISDK sdk = mMapSDK.get(key);
               sdk.onResume();
           }
       }
    }


    public void onPause(boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onPause();
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onPause();
            }
        }
    }


    public void onDestroy(boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onDestroy();
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onDestroy();
            }
        }
    }


    public void onStart(boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onStart();
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onStart();
            }
        }
    }


    public void onStop(boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onStop();
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onStop();
            }
        }
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data, boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onActivityResult(requestCode, resultCode, data);
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void onSaveInstanceState(Bundle outState, boolean defaultSDK)
    {
        if(defaultSDK && null != mDefaultSDK)
        {
            mDefaultSDK.onSaveInstanceState(outState);
        }
        else
        {
            Set<String> allKey = mMapSDK.keySet();
            Iterator<String> iterator = allKey.iterator();
            while(iterator.hasNext())
            {
                String key = iterator.next();
                ISDK sdk = mMapSDK.get(key);
                sdk.onSaveInstanceState(outState);
            }
        }
    }

	protected long mMainThreadId;
	
	protected ISDK mDefaultSDK;
	
	protected HashMap<String, ISDK> mMapSDK;
	
	protected static int GenSDKInstanceMsg = 1;
}

/**
 * 所有sdk的汇总中心
 * @author zhuang yusong
 *
 */
public class SDKCenter{
	
	public static SDKCenter getInstance(){
		if(null == SDKCenter.sIntance)
			SDKCenter.sIntance = new SDKCenter();
		return SDKCenter.sIntance;
	}

	public void init(){
		if(mIsInit)
			return;
		mIsInit = true;
		mAccount = new SDKInstanceInfo();
		mPurchase = new SDKInstanceInfo();
		mAnalysis = new SDKInstanceInfo();
        mShare = new SDKInstanceInfo();
		mHandler = new Handler();
        mMainThreadId = Thread.currentThread().getId();
	}
	
	protected Handler getHandler(){
		return mHandler;
	}
	
	/**
	 * 获取账号sdk实例
	 * @return
	 */
	public static AccountSDK account(){
	    SDKCenter center = SDKCenter.getInstance();
	    return (AccountSDK)center.mAccount.getDefaultSDK();
	}
	
	/**
	 * 获取支付sdk实例
	 * @return
	 */
	public static PurchaseSDK purchase(){
	    SDKCenter center = SDKCenter.getInstance();
	    return (PurchaseSDK)center.mPurchase.getDefaultSDK();
	}

    /**
     * 获取分享sdk实例
     * @return
     */
    public static ShareSDK share(){
        SDKCenter center = SDKCenter.getInstance();
        return (ShareSDK)center.mShare.getDefaultSDK();
    }

	/**
	 * 获取统计sdk实例
	 * @return
	 */
	public static HashMap<String, ISDK> analysis(){
	    SDKCenter center = SDKCenter.getInstance();
	    return center.mAnalysis.mapSDK();	    
	}
	
	/**
	 * 添加账号sdk
	 * @param 
	 */
	public static void addAccountSDK(AccountSDK account){
		SDKCenter center = SDKCenter.getInstance();
		center.mAccount.addSDK(account);
	}
	
	/**
	 * 添加支付sdk
	 * @param 
	 */
	public static void addPurchaseSDK(PurchaseSDK purchase){
		SDKCenter center = SDKCenter.getInstance();
		center.mPurchase.addSDK(purchase);
	}

	/**
	 * 添加统计sdk
	 * @param 
	 */
	public static void addAnalysisSDK(AnalysisSDK analysis){
		SDKCenter center = SDKCenter.getInstance();
		center.mPurchase.addSDK(analysis);
	}

    /**
     * 添加分享sdk
     * @param
     */
    public static void addShareSDK(ShareSDK share){
        SDKCenter center = SDKCenter.getInstance();
        center.mShare.addSDK(share);
    }

	/**
	 * 添加默认账号sdk
	 * @param 
	 */
	public static void setDefaultAccountSDK(AccountSDK account){
		SDKCenter center = SDKCenter.getInstance();
		center.mAccount.setDefaultSDK(account);
	}

	/**
	 * 添加默认支付sdk
	 * @param 
	 */
	public static void setDefaultPurchaseSDK(PurchaseSDK purchase){
		SDKCenter center = SDKCenter.getInstance();
		center.mPurchase.setDefaultSDK(purchase);
	}
	
	/**
	 * 添加默认统计sdk
	 * @param 
	 */	
	public static void setDefaultAnalysisSDK(AnalysisSDK analysis){
		SDKCenter center = SDKCenter.getInstance();
		center.mAnalysis.setDefaultSDK(analysis);
	}

    /**
     * 添加默认分享sdk
     * @param
     */
    public static void setDefaultShareSDK(ShareSDK share)
    {
        SDKCenter center = SDKCenter.getInstance();
        center.mShare.setDefaultSDK(share);
    }

	/**
	 * 设置账号sdk的默认sdk类名
	 * @param className
	 */
	public static void setDefaultAccountSDKByClassName(String className){
		 SDKCenter sdkCenter = SDKCenter.getInstance();
		 sdkCenter.mAccount.setDefaultSDKByClassName(className);
	}
	
	/**
	 * 设置支付sdk的默认sdk类名
	 * @param className
	 */
	public static void setDefaultPurchaseSDKByClassName(String className){
		 SDKCenter sdkCenter = SDKCenter.getInstance();
		 sdkCenter.mPurchase.setDefaultSDKByClassName(className);
	}

	/**
	 * 设置统计sdk的默认sdk类名
	 * @param className
	 */
	public static void setDefaultAnalysisSDKByClassName(String className){
		 SDKCenter sdkCenter = SDKCenter.getInstance();
		 sdkCenter.mAnalysis.setDefaultSDKByClassName(className);
	}
	
	
	/**
	 * 设置是否竖屏
	 * @param isPortrait
	 */
	public static void setIsPortrait(boolean isPortrait){
		SDKCenter center = SDKCenter.getInstance();
		center.mIsPortrait = isPortrait;
	}

	/**
	 * 设置是否自动旋转
	 * @param autoRotation
	 */
	public static void setIsAutoRotation(boolean autoRotation){
		SDKCenter center = SDKCenter.getInstance();
		center.mIsAutoRotation = autoRotation;
	}
	
	/**
	 * 是否支持横屏
	 * @return
	 */
	public static boolean isLandscape(){
		SDKCenter center = SDKCenter.getInstance();
		return !center.mIsPortrait;
	}

	/**
	 * 是否支持自动旋转
	 * @return
	 */
	public static boolean isAutoRotation(){
		SDKCenter center = SDKCenter.getInstance();
		return center.mIsAutoRotation;
	}

    /**
     * 判断是否已经有这个sdk了
     * @param sdkClassName
     * @return
     */
    public static boolean hasThisSDK(String sdkClassName)
    {
        SDKCenter center = SDKCenter.getInstance();
        if(center.mAccount.hasSDK(sdkClassName))
            return true;
        if(center.mPurchase.hasSDK(sdkClassName))
            return true;
        if(center.mAnalysis.hasSDK(sdkClassName))
            return true;
        else if(center.mShare.hasSDK(sdkClassName))
            return true;
        else
            return false;
    }

    public static long getMainThreadId()
    {
        SDKCenter center = SDKCenter.getInstance();
        return center.mMainThreadId;
    }

    // 以下函数都是为了能够被Activity的on系列函数能够被调用
    public static void onCreate(final Bundle savedInstanceState)
    {
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onCreate(savedInstanceState, true);
        center.mShare.onCreate(savedInstanceState, true);
        center.mPurchase.onCreate(savedInstanceState, true);
        center.mAnalysis.onCreate(savedInstanceState, false);
    }

    public static void onResume(){
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onResume(true);
        center.mShare.onResume(true);
        center.mPurchase.onResume(true);
        center.mAnalysis.onResume(false);
    }


    public static void onPause(){
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onPause(true);
        center.mShare.onPause(true);
        center.mPurchase.onPause(true);
        center.mAnalysis.onPause(false);
    }


    public static void onDestroy(){
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onDestroy(true);
        center.mShare.onDestroy(true);
        center.mPurchase.onDestroy(true);
        center.mAnalysis.onDestroy(false);
    }


    public static  void onStart(){
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onStart(true);
        center.mShare.onStart(true);
        center.mPurchase.onStart(true);
        center.mAnalysis.onStart(false);
    }


    public static  void onStop(){
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onStop(true);
        center.mShare.onStop(true);
        center.mPurchase.onStop(true);
        center.mAnalysis.onStop(false);
    }


    public static void onActivityResult(int requestCode, int resultCode, Intent data){
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onActivityResult(requestCode, resultCode, data, true);
        center.mShare.onActivityResult(requestCode, resultCode, data, true);
        center.mPurchase.onActivityResult(requestCode, resultCode, data, true);
        center.mAnalysis.onActivityResult(requestCode, resultCode, data, false);
    }

    public static void onSaveInstanceState(Bundle outState)
    {
        SDKCenter center = SDKCenter.getInstance();
        center.mAccount.onSaveInstanceState(outState, true);
        center.mShare.onSaveInstanceState(outState, true);
        center.mPurchase.onSaveInstanceState(outState, true);
        center.mAnalysis.onSaveInstanceState(outState, false);
    }



	protected static SDKCenter sIntance = null;
	
	protected SDKInstanceInfo mAccount;
	
	protected SDKInstanceInfo mPurchase;
	
	protected SDKInstanceInfo mAnalysis;

    protected SDKInstanceInfo mShare;

	protected Handler mHandler;
	
	protected boolean mIsInit = false;
	
	protected boolean mIsPortrait;

	protected boolean mIsAutoRotation;

    protected long mMainThreadId;

    public static final String DefaultAccoutSDKName = "com.lib.x.AccountSDK";

    public static final String DefaultAnalysisSDKName = "com.lib.x.AnalysisSDK";

    public static final String DefaultPurchaseSDKName = "com.lib.x.PurchaseSDK";

    public static final String DefaultShareSDKName = "com.lib.x.ShareSDK";
}
