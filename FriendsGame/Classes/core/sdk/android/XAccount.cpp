#include "../XSDKCenter.h"
#include "XSdkJniHelper.h"
#include "../../thread/XRunLoop.h"

std::string s_accountTemp;
#define AccountSDKClass     "com/lib/x/AccountSDK"

Account::Account()
{
    m_delegate = nullptr;
}

Account::~Account(){
}

void Account::prepare(){
	CCLOG("Account::prepare");
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::prepareSDK(account);
}

bool Account::isDefault(){
	return true;
}

bool Account::hasUserCenter(){
	return false;
}

const char* Account::getName(){
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getName");
	return s_accountTemp.c_str();
}

const char* Account::getId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getAccountId");
	return s_accountTemp.c_str();
}

const char* Account::getSessionId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getSessionId");
	return s_accountTemp.c_str();
}

const char* Account::getGender()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getGender");
	return s_accountTemp.c_str();
}

const char* Account::getFirstName()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getFirstName");
	return s_accountTemp.c_str();
}

const char* Account::getLastName()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getLastName");
	return s_accountTemp.c_str();
}

const char* Account::getLocale()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getLocale");
	return s_accountTemp.c_str();
}

const char* Account::getEmail()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getEmail");
	return s_accountTemp.c_str();
}

const char* Account::getProfileImage()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getProfileImage");
	return s_accountTemp.c_str();
}

const char* Account::getChannelId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getChannelId");
	return s_accountTemp.c_str();
}

const char* Account::getChannelName()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getChannelName");
	return s_accountTemp.c_str();
}

const char* Account::getAppId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getAppId");
	return s_accountTemp.c_str();
}

const char* Account::getAppKey()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getAppKey");
	return s_accountTemp.c_str();
}
	
void Account::clean(){
	
}

void Account::login(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "loginInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
}

void Account::logout(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "logoutInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
	if (m_delegate)
		m_delegate->didLogoutFinished(LogoutFromManual);
}

void Account::swtichAccount(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "swtichAccountInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
}

void Account::gotoUserCetner(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "gotoUserCetnerInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
}

void Account::gotoBBS(){	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "gotoBBSInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
}

void Account::gotoEnterAPPCetner(){
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "gotoEnterAPPCetnerInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
}

void Account::showToolbar(bool visible){
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "showToolbarInGLThread", "(Z)V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID, visible);	
}

void Account::showToolbar(bool visible, AccountToolBarPlace place){
 	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "showToolbarInGLThread", "(ZI)V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID, visible, (int)place);	 
}

const char* Account::getOtherInfo(const char* key){
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getOtherInfo(account, AccountSDKClass, key);
	return s_accountTemp.c_str();
}

void Account::setOtherInfo(const char* key, const char* value){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::setOtherInfo(account, AccountSDKClass, key, value);
}

void Account::callFuntionBegin(){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::callFuntionBegin(account, AccountSDKClass);
}

void Account::addFunctionParam(const char* key, const char* value){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::addFunctionParam(account, AccountSDKClass, key, value);
}

void Account::callFunction(const char* name){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::callFunction(account, AccountSDKClass, name);
}

void Account::callFunctionEnd(){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::callFunctionEnd(account, AccountSDKClass);
}

void Account::setDefaultAccountSDKByClassName(const char* className){
	SdkJniHelper::setDefaultXXXSDKByClassName("setDefaultAccountSDKByClassName", className);
}

void Account::setSyncParam(const char* key, const char* value){	
}

const char* Account::getSyncParam(const char* key){
	s_accountTemp.clear();
	return s_accountTemp.c_str();
}

std::map<std::string, std::string> Account::getAllSyncParam(){
	std::map<std::string, std::string> allSync;
	return allSync;
}

void Account::cleanSyncParam(){
}
	
void Account::setDelegate(AccountDelegate* _delegate){
    AccountDelegate* oldDelegate = m_delegate;
    m_delegate = _delegate;
    if (m_delegate){
        Ref* obj =	dynamic_cast<Ref*>(m_delegate);
        if (obj)
        obj->retain();
    }
    
    if (oldDelegate){
        Ref* obj = dynamic_cast<Ref*>(m_delegate);
        if (obj)
        obj->release();
        else
        delete oldDelegate;
    }
}

AccountDelegate* Account::getDelegate()
{ 
    return m_delegate;
}

///////////////////////////////////////////////////////////////////////////////
class AccountRunLoopObserver : public RunLoopObserver
{
public:

	AccountRunLoopObserver(const char* operate, const char* errorCode, int from)
	{
		m_from = from;
		m_operate = operate;
		if(errorCode)
			m_errorCode = errorCode;
	}

	virtual void operate()
	{
		Account* account = SDKCenter::getAccount();
	    AccountDelegate* _delegate = account->getDelegate();
	    if(nullptr == _delegate)
	        return;

	    if(m_operate == "didLoginFinished")
	    {
	    	const char* szError = m_errorCode.c_str();
	    	if(szError[0] == 0)
	    		szError = nullptr;
 			_delegate->didLoginFinished(szError);
	    }
	    else if(m_operate == "didLogoutFinished")
	    {
	    	_delegate->didLogoutFinished((AccountLogoutFrom)m_from);
	    }
	    else if(m_operate == "didLoginCancel")
	    {
	    	_delegate->didLoginCancel();
	    }
	   
	}

	void post()
	{
		RunLoop::getInstance()->addObserver(this);
	}

public:

	std::string m_errorCode;

	std::string m_operate;

	int m_from;
};

///////////////////////////////////////////////////////////////////////////////
 extern "C" {
JNIEXPORT void JNICALL Java_com_lib_x_AccountSDK_didLoginFinished(JNIEnv* env, jclass jthis, jstring errorCode)
{
	std::string strError;
    if(errorCode){
	    strError = cocos2d::JniHelper::jstring2string(errorCode);
    }
    AccountRunLoopObserver* observer = new AccountRunLoopObserver("didLoginFinished", strError.c_str(), 0);
    observer->post();
}

JNIEXPORT void JNICALL Java_com_lib_x_AccountSDK_didLogoutFinished(JNIEnv* env, jclass jthis, jint from)
{
	AccountRunLoopObserver* observer = new AccountRunLoopObserver("didLoginFinished", "", from);
	observer->post();
}

JNIEXPORT void JNICALL Java_com_lib_x_AccountSDK_didLoginCancel(JNIEnv* env, jclass jthis)
{
    AccountRunLoopObserver* observer = new AccountRunLoopObserver("didLoginCancel", "", 0);
    observer->post();
}

}