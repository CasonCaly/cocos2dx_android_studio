package com.lib.x;

import android.os.Bundle;
import android.os.Message;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * 
 * @author zhuang yusong
 *
 */
public class AccountSDK extends ISDK{

	public static class Friend
	{
		public void setId(String id)
		{
			if(null != id)
				m_id = id;
		}

		public String getId()
		{
			return m_id;
		}


		public void setProfileImage(String profileImage)
		{
			if(null != profileImage)
				m_profileImage = profileImage;
		}

		public String getProfileIamge()
        {
            return m_profileImage;
        }

		public void setName(String name)
		{
			if(null != name)
				m_name = name;
		}

        public String getName()
        {
            return m_name;
        }

		public void setGender(String gender)
		{
			if (null != gender)
				m_gender = gender;
		}

        public String getGender()
        {
            return m_gender;
        }

        public void setFirstName(String firstName)
        {
            if(null != firstName)
                m_firstName = firstName;
        }

        public String getFirstName()
        {
            return m_firstName;
        }

        public void setMiddleName(String middleName)
        {
            if(null != middleName)
                m_middleName = middleName;
        }

        public String getMiddleName()
        {
            return m_middleName;
        }

        public void setLastName(String lastName)
        {
            if(null != lastName)
                m_lastName = lastName;
        }

        public String getLastName()
        {
            return m_lastName;
        }


		protected String m_id  = "";

		protected String m_profileImage = "";

		protected String m_name = "";

        protected String m_firstName = "";

        protected String m_middleName = "";

        protected String m_lastName = "";

		protected String m_gender = "";
	}

	public AccountSDK(){
		super();
	}

	public boolean isDefault()
	{
		String className = this.getClass().getName();
		if(className.equals(SDKCenter.DefaultAccoutSDKName))
			return true;
		else
			return false;
	}
	
//以下函数都可以被复写
	/**
	 * 登陆
	 */
	public void login(){}
	
	/**
	 * 登出
	 */
	public void logout(){}

	/**
	 * 切换通平台的账号
	 */
	public void swtichAccount(){}

	/**
	 * 去用户中心
	 */
	public void gotoUserCetner(){}

	/**
	 * 去bbs中心，如果有的话
	 */
	public void gotoBBS(){}

	/**
	 * 去应用下载中心，如果有的话
	 */
	public void gotoEnterAPPCetner(){}

	/**
	 * 显示悬浮窗
	 * @param visible
	 */
	public void showToolbar(boolean visible){}

	/**
	 * 显示悬浮窗，并带位置
	 * @param visible
	 * @param place
	 */
	public void showToolbar(boolean visible, int place){}
	
//以下都是功能性函数，可以被子类调用
	/**
	 * 清理
	 */
	public void clear(){
		super.clear();
		mAccountId = "";	
		mGender = "";		
		mName = "";	
		mFirstName = "";	
		mLastName = "";	
		mLocale = "";		
		mEmail = "";		
		mProfileImage = "";
		mSessionId = "";
        m_mapFriend.clear();
        m_listFriend.clear();
	}

	/**
	 * 调用成功后通知
	 * @param error
	 */
	public void notifLoginFinished(String error){
		AccountSDK.didLoginFinished(error);
	}

	/**
	 * 登陆取消后的通知
	 */
	public void notifLoginCancel(){AccountSDK.didLoginCancel();}

	/**
	 * 登出通知
	 */
	public void notifyLogoutFinished(){
		AccountSDK.didLogoutFinished(mLogoutFrom);	
	}
	
	/**
	 * 用于处理从gl线程抛到主线程的消息
	 */
	public void handleMessage(Message msg){
		super.handleMessage(msg);
		int what = msg.what;
		switch(what)
		{
			case AccountSDK.LoginMsg:{
				this.login();
				break;
			}
			case AccountSDK.LogoutMsg:{
				this.logout();
				break;
			}
			case AccountSDK.GotoUserCetnerMsg:{
				this.gotoUserCetner();
				break;
			}
			case AccountSDK.GotoBBSMsg:{
				this.gotoBBS();
				break;
			}
			case AccountSDK.GotoEnterAPPCetnerMsg:{
				this.gotoEnterAPPCetner();
				break;
			}
			case AccountSDK.SwtichAccountMsg:{
				this.swtichAccount();
			}
			case AccountSDK.ShowToolbarMsg:{
				Bundle bundle =	msg.getData();
				boolean visible = bundle.getBoolean("visible");
				this.showToolbar(visible);
			}
			case AccountSDK.ShowToolbarWithPlaceMsg:{
				Bundle bundle =	msg.getData();
				boolean visible = bundle.getBoolean("visible");
				int place = bundle.getInt("place");
				this.showToolbar(visible, place);
			}
		}
	}

//以下都是在gl线程调用的函数
	/**
	 * 在gl线程调用登陆
	 */
	protected void loginInGLThread(){
		Message msg = new Message();
		msg.what = LoginMsg;
		this.sendMessage(msg);			
	}
	
	/**
	 * 在gl线程调用登出
	 */
	protected void logoutInGLThread(){
		Message msg = new Message();
		msg.what = LogoutMsg;
		this.sendMessage(msg);		
	}
	
	/**
	 * 在gl线程调用切换账号
	 */
	protected void swtichAccountInGLThread(){
		Message msg = new Message();
		msg.what = SwtichAccountMsg;
		this.sendMessage(msg);	
	}

	/**
	 * 在gl线程调用去用户中心
	 */
	protected void gotoUserCetnerInGLThread(){
		Message msg = new Message();
		msg.what = GotoUserCetnerMsg;
		this.sendMessage(msg);	
	}

	/**
	 * 在gl线程调用去bbs中心
	 */
	protected void gotoBBSInGLThread(){
		Message msg = new Message();
		msg.what = GotoBBSMsg;
		this.sendMessage(msg);	
	}

	/**
	 * 在gl线程调用去应用中心
	 */
	protected void gotoEnterAPPCetnerInGLThread(){
		Message msg = new Message();
		msg.what = GotoEnterAPPCetnerMsg;
		this.sendMessage(msg);	
	}

	/**
	 * 在gl线程调用是否显示悬浮窗
	 * @param visible
	 */
	protected void showToolbarInGLThread(boolean visible){
		Message msg = new Message();
		Bundle data = new Bundle();
		data.putBoolean("visible", visible);
		msg.what = ShowToolbarMsg;
		msg.setData(data);
		this.sendMessage(msg);
	}

	/**
	 * 在gl线程调用是否显示悬浮窗并带位置
	 * @param visible
	 * @param place
	 */
	protected void showToolbarInGLThread(boolean visible, int place){
		Message msg = new Message();
		msg.what = ShowToolbarWithPlaceMsg;
		Bundle data = new Bundle();
		data.putBoolean("visible", visible);
		data.putInt("place", place);
		msg.what = ShowToolbarMsg;
		msg.setData(data);
		this.sendMessage(msg);
	}
	

//以下都是和用户属性访问有关系的函数	
	/**
	 * 设置账号id
	 * @param accoundId
	 */
	public void setAccountId(String accoundId){
		if(null != accoundId)
			mAccountId = accoundId;
	}
	
	/**
	 * 获取账号id
	 * @return
	 */
	public String getAccountId(){
		return mAccountId;
	}
	
	/**
	 * 设置用户性别
	 * @param gender
	 */
	public void setGender(String gender){
		if(null != gender)
			mGender = gender;
	}
	
	/**
	 * 获取性别
	 * @return
	 */
	public String getGender(){
		return mGender;
	}
	
	/**
	 * 设置昵称或者全名
	 * @param name
	 */
	public void setName(String name){
		if(null != name)
			mName = name;
	}
	
	/**
	 * 获取昵称或者全名
	 * @return
	 */
	public String getName(){
		return mName;
	}
	
	/**
	 * 设置用户的名
	 * @param firstName
	 */
	public void setFirstName(String firstName){
		if(null != firstName)
			mFirstName = firstName;
	}
	
	/**
	 * 获取用户的名
	 * @return
	 */
	public String getFirstName(){
		return mFirstName;
	}
	
	/**
	 * 设置用户的姓
	 * @param lastName
	 */
	public void setLastName(String lastName){
		if(null != lastName)
			mLastName = lastName;
	}
	
	/**
	 * 获取用户的姓
	 * @return
	 */
	public String getLastName(){
		return mLastName;
	}
	
	/**
	 * 设置用户的地址
	 * @param locale
	 */
	public void setLocale(String locale){
		if(null != locale)
			mLocale = locale;
	}
	
	/**
	 * 获取用户的地址信息
	 * @return
	 */
	public String getLocale(){
		return mLocale;
	}
	
	/**
	 * 设置用户的邮箱
	 * @param email
	 */
	public void setEmail(String email){
		if(null != email)
			mEmail = email;
	}
	
	/**
	 * 获取用户的邮箱
	 * @return
	 */
	public String getEmail(){
		return mEmail;
	}
	
	/**
	 * 设置头像的url地址
	 * @param profileImage
	 */
	public void setProfileImage(String profileImage){
		if(null != profileImage)
			mProfileImage = profileImage;
	}
	
	/**
	 * 获取头像的ulr地址
	 * @return
	 */
	public String getProfileImage(){
		return mProfileImage;
	}
	
	/**
	 * 设置session id
	 * @param sessionId
	 */
	public void setSessionId(String sessionId){
		if(null != sessionId)
			mSessionId = sessionId;
	}
	
	/**
	 * 获取session id
	 * @return
	 */
	public String getSessionId(){
		return mSessionId;
	}

	/**
	 * 获取渠道id
	 * @return
	 */
	public String getChannelId(){
		return mChannelId;
	}
	
	public String getChannelName(){
		return mChannelName;
	}
	
	public String getAppKey(){
		return mAppKey;
	}
	
	public String getAppId(){
		return mAppId;
	}

    public void addFriend(Friend friend)
    {
        if(null == friend)
            return;
        String id = friend.getId();
        Friend target = m_mapFriend.get(id);
        if(null == target)
        {
            m_mapFriend.put(id, friend);
            m_listFriend.add(friend);
        }
    }

    public int getFriendCount()
    {
        return m_listFriend.size();
    }

    public Friend getFriend(int index)
    {
        if(index >= m_listFriend.size())
            return null;
        return m_listFriend.get(index);
    }

	private static native void didLoginFinished(String error);

	private static native void didLoginCancel();

	private static native void didLogoutFinished(int from);

//以下都是sdk的配置属性
	
	protected String mChannelId = "100";//渠道信息
	
	protected String mChannelName = "";//渠道名称
	
	protected String mAppKey = ""; //应用的app key
	
	protected String mAppId = "";  //应用的app id
	
	protected int mLogoutFrom = 0;
	
//以下都是用户属性
	
	protected String mAccountId = "";   //第三方的账号id
	
	protected String mGender = "";		//性别
	
	protected String mName = "";		//全名
	
	protected String mFirstName = "";   //名
	
	protected String mLastName = "";   //性
	
	protected String mLocale = "";     //位置信息
	
	protected String mEmail = "";	   //邮件
	
	protected String mProfileImage = "";//自己的头像地址
	
	protected String mEmpty = "";		//辅助空字符
	
	protected String mSessionId = "";   //有些平台需要sessionid

    protected HashMap<String, Friend> m_mapFriend = new HashMap<String, Friend>();

    protected ArrayList<Friend> m_listFriend = new ArrayList<Friend>();

//以下都是和悬浮图片有关的方向
    public static int SDKToolBarAtTopLeft = 1;     /* 左上 */
    	    
	public static int SDKToolBarAtTopRight = 2;    /* 右上 */

	public static int SDKToolBarAtMiddleLeft = 3;  /* 左中 */

	public static int SDKToolBarAtMiddleRight = 4; /* 右中 */

	public static int SDKToolBarAtBottomLeft = 5;  /* 左下 */

	public static int SDKToolBarAtBottomRight = 6; /* 右下 */ 
	
//以下都是和安卓的Handler send message有关的
	private final static int LoginMsg = 1;      //登陆
	
	private final static int LogoutMsg = 2;		//登出
	
	private final static int SwtichAccountMsg = 3; //手动切换账号
	
	private final static int GotoUserCetnerMsg = 4;//去用户中心
	
	private final static int GotoBBSMsg = 5;	   //有些平台有bbs这样的用户中心
	
	private final static int GotoEnterAPPCetnerMsg = 6;//有些平台有应用中心这样的需求
	
	private final static int ShowToolbarMsg = 7;   //是否显示悬浮工具小窗
	
	private final static int ShowToolbarWithPlaceMsg = 8;//显示并设置悬浮小窗的位置
	
// 登出的类型
	
	public static int LogoutManual = 1; //手动登出
	
	public static int LogoutAuto = 2;  //自动登出，比如从用户中心登出
}
