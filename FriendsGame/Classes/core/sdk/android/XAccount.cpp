#include "../XSDKCenter.h"
#include "XSdkJniHelper.h"
#include "../../thread/XRunLoop.h"

std::string s_accountTemp;
#define AccountSDKClass     "com/lib/x/AccountSDK"
#define AccountFriendClass     "com/lib/x/AccountSDK$Friend"

void AccountFriend::setId(const char* id)
{
	if(nullptr != id)
		m_id = id;
}

const char* AccountFriend::getId()
{
	return m_id.c_str();
}


void AccountFriend::setProfileImage(const char* profileImage)
{
	if(nullptr != profileImage)
		m_profileImage = profileImage;
}

const char* AccountFriend::getProfileIamge()
{
	return m_profileImage.c_str();
}

void AccountFriend::setName(const char* name)
{
	if(nullptr != name)
		m_name = name;
}

const char* AccountFriend::getName()
{
	return m_name.c_str();
}

void AccountFriend::setGender(const char* gender)
{
	if (nullptr != gender)
		m_gender = gender;
}

const char* AccountFriend::getGender()
{
	return m_gender.c_str();
}

void AccountFriend::setFirstName(const char* firstName)
{
	if(nullptr != firstName)
		m_firstName = firstName;
}

const char* AccountFriend::getFirstName()
{
	return m_firstName.c_str();
}

void AccountFriend::setMiddleName(const char* middleName)
{
	if(nullptr != middleName)
		m_middleName = middleName;
}

const char* AccountFriend::getMiddleName()
{
	return m_middleName.c_str();
}

void AccountFriend::setLastName(const char* lastName)
{
	if(nullptr == lastName)
		m_lastName = lastName;
}

const char* AccountFriend::getLastName()
{
	return m_lastName.c_str();
}
	
///////////////////////////////////////////////////////////////////////	
Account::Account()
{
    m_delegate = nullptr;
	m_friendList.init();
}

Account::~Account(){
}

void Account::prepare(){
	CCLOG("Account::prepare");
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::prepareSDK(account);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
}

bool Account::isDefault(){
	jobject account = SdkJniHelper::getAccount();
	bool isDefault = SdkJniHelper::isDefault(account);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return isDefault;
}

bool Account::hasUserCenter(){
	return false;
}

const char* Account::getName(){
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getName");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
	
}

const char* Account::getId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getAccountId");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

const char* Account::getSessionId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getSessionId");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);	
	return s_accountTemp.c_str();
}

const char* Account::getGender()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getGender");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);	
	return s_accountTemp.c_str();
}

const char* Account::getFirstName()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getFirstName");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);	
	return s_accountTemp.c_str();
}

const char* Account::getLastName()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getLastName");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);	
	return s_accountTemp.c_str();
}

const char* Account::getLocale()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getLocale");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);	
	return s_accountTemp.c_str();
}

const char* Account::getEmail()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getEmail");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

const char* Account::getProfileImage()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getProfileImage");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

const char* Account::getChannelId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getChannelId");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

const char* Account::getChannelName()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getChannelName");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

const char* Account::getAppId()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getAppId");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

const char* Account::getAppKey()
{
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getXXXReturnString(account, AccountSDKClass, "getAppKey");
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}
	
void Account::clean(){
	m_friendList.removeAllObjects();
}

void Account::login(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "loginInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Account::logout(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "logoutInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);

	if (m_delegate)
		m_delegate->didLogoutFinished(LogoutFromManual);
}

void Account::swtichAccount(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "swtichAccountInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Account::gotoUserCetner(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "gotoUserCetnerInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Account::gotoBBS(){	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "gotoBBSInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Account::gotoEnterAPPCetner(){
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "gotoEnterAPPCetnerInGLThread", "()V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID);
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);	
}

void Account::showToolbar(bool visible){
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "showToolbarInGLThread", "(Z)V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID, visible);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Account::showToolbar(bool visible, AccountToolBarPlace place){
 	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "showToolbarInGLThread", "(ZI)V"))
		return ;
	jobject account = SdkJniHelper::getAccount();
	methodInfo.env->CallVoidMethod(account, methodInfo.methodID, visible, (int)place);	
	methodInfo.env->DeleteLocalRef(account);
	methodInfo.env->DeleteLocalRef(methodInfo.classID); 
}

const char* Account::getOtherInfo(const char* key){
	s_accountTemp.clear();
	jobject account = SdkJniHelper::getAccount();
	s_accountTemp = SdkJniHelper::getOtherInfo(account, AccountSDKClass, key);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
	return s_accountTemp.c_str();
}

void Account::setOtherInfo(const char* key, const char* value){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::setOtherInfo(account, AccountSDKClass, key, value);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
}

void Account::callFuntionBegin(){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::callFuntionBegin(account, AccountSDKClass);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
}

void Account::addFunctionParam(const char* key, const char* value){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::addFunctionParam(account, AccountSDKClass, key, value);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
}

void Account::callFunction(const char* name){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::callFunction(account, AccountSDKClass, name);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
}

void Account::callFunctionEnd(){
	jobject account = SdkJniHelper::getAccount();
	SdkJniHelper::callFunctionEnd(account, AccountSDKClass);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(account);
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

void Account::addFriend(AccountFriend* accountFriend)
{
	m_friendList.addObject(accountFriend);
}

int Account::getFriendCount()
{
	return (int)m_friendList.count();
}

AccountFriend* Account::getFriend(int index)
{
	if(index >= m_friendList.count())
		return nullptr;
	Ref* objFriend = m_friendList.getObjectAtIndex(index);
	return dynamic_cast<AccountFriend*>(objFriend);
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
			account->clean();	
			JNIEnv* env = JniHelper::getEnv();
			jobject jAccount = SdkJniHelper::getAccount();
			int count = SdkJniHelper::getFriendCount(jAccount);

			for(int i = 0; i < count; i++)
			{
				jobject jFriend = SdkJniHelper::getFriend(jAccount, i);
				AccountFriend* accountFriend = new AccountFriend();
				std::string strId = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getId");
				accountFriend->setId(strId.c_str());
				std::string strProfileImage = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getProfileIamge");
				accountFriend->setProfileImage(strProfileImage.c_str());
				std::string strName = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getName");
				accountFriend->setName(strName.c_str());
				std::string strGender = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getGender");
				accountFriend->setGender(strGender.c_str());	
				std::string strFirstName = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getFirstName");
				accountFriend->setFirstName(strFirstName.c_str());
				std::string strMiddleName = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getMiddleName");
				accountFriend->setMiddleName(strMiddleName.c_str());
				std::string strLastname = SdkJniHelper::getXXXReturnString(jFriend, AccountFriendClass, "getLastName");
				accountFriend->setLastName(strLastname.c_str());
				account->addFriend(accountFriend);
				accountFriend->release();
				
				env->DeleteLocalRef(jFriend);
			}
			env->DeleteLocalRef(jAccount);
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