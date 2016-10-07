#include "../XSDKCenter.h"
#include "XSdkJniHelper.h"
#include "../../thread/XRunLoop.h"

std::string s_temp;
#define PurchaseSDKClass     "com/lib/x/PurchaseSDK"


Purchase::Purchase(){
    m_delegate = nullptr;
}

Purchase::~Purchase(){
    
}

void Purchase::prepare(){
	jobject purchase = SdkJniHelper::getPurchase();
	if(purchase){
		SdkJniHelper::prepareSDK(purchase);
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

bool Purchase::isDefault(){
	jobject purchase = SdkJniHelper::getPurchase();
	return SdkJniHelper::isDefault(purchase);
}

void Purchase::startPurchase(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, PurchaseSDKClass, "startPurchaseInGLThread", "()V"))
		return ;
	jobject purchase = SdkJniHelper::getPurchase();
	if(nullptr == purchase)
		return;
	methodInfo.env->CallVoidMethod(purchase, methodInfo.methodID);
	methodInfo.env->DeleteLocalRef(purchase);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);	 
}

void Purchase::setOrderSerial(const char* orderSerial){	
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setOrderSerial", orderSerial);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setPrice(float price){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, PurchaseSDKClass, "setPrice", "(F)V"))
		return ;
	
	jobject purchase = SdkJniHelper::getPurchase();
	methodInfo.env->CallVoidMethod(purchase, methodInfo.methodID, price);	
	methodInfo.env->DeleteLocalRef(purchase);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);	  
}
	
void Purchase::setPayId(const char* payId){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setPayId", payId);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setProductId(const char* productId){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setProductId", productId);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setGameUserServer(const char* gameUserServer){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setGameUserServer", gameUserServer);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setName(const char* name){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setName", name);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setGameUserId(const char* userId){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setGameUserId", userId);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setGameUserName(const char* userName){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setGameUserName", userName);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setPayUrl(const char* payUrl){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setPayUrl", payUrl);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

void Purchase::setProductType(const char* szProductType){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setXXXWithString(purchase, PurchaseSDKClass, "setProductType", szProductType);
	if(purchase){
		JNIEnv* env = JniHelper::getEnv();
		env->DeleteLocalRef(purchase);
	}
}

const char* Purchase::getOtherInfo(const char* key){
   	s_temp.clear();
	jobject purchase = SdkJniHelper::getPurchase();
	s_temp = SdkJniHelper::getOtherInfo(purchase, PurchaseSDKClass, key);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(purchase); 
	return s_temp.c_str();
}

void Purchase::setOtherInfo(const char* key, const char* value){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setOtherInfo(purchase, PurchaseSDKClass, key, value);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(purchase); 
}

void Purchase::callFuntionBegin(){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::callFuntionBegin(purchase, PurchaseSDKClass);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(purchase); 
}

void Purchase::addFunctionParam(const char* key, const char* value){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::addFunctionParam(purchase, PurchaseSDKClass, key, value);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(purchase); 
}

void Purchase::callFunction(const char* name){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::callFunction(purchase, PurchaseSDKClass, name);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(purchase); 
}

void Purchase::callFunctionEnd(){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::callFunctionEnd(purchase, PurchaseSDKClass);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(purchase); 
}

void Purchase::setDefaultPurchaseClass(const char* className){

}

void Purchase::setSyncParam(const char* key, const char* value)
{	
}

const char* Purchase::getSyncParam(const char* key)
{
	s_temp.clear();
	return s_temp.c_str();
}

std::map<std::string, std::string> Purchase::getAllSyncParam()
{
	std::map<std::string, std::string> allSync;
	return allSync;
}

void Purchase::cleanSyncParam()
{
}

PurchaseDelegate* Purchase::getDelegate(){
    return m_delegate;
}

void Purchase::setDelegate(PurchaseDelegate* _delegate){
    PurchaseDelegate* oldDelegate = m_delegate;
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

///////////////////////////////////////////////////////////////////////////////
class PurchaseRunLoopObserver : public RunLoopObserver
{
public:

	PurchaseRunLoopObserver(const char* operate, const char* errorCode)
	{
		m_operate = operate;
		if(errorCode)
			m_errorCode = errorCode;
	}

	virtual void operate()
	{
		Purchase* purchase = SDKCenter::getPurchase();
	    PurchaseDelegate* _delegate = purchase->getDelegate();
	    if(nullptr == _delegate)
	        return;

	    if(m_operate == "didPurchaseFinish"){
			const char* szError = m_errorCode.c_str();
	    	if(szError[0] == 0)
	    		szError = nullptr;
	    	_delegate->didPurchaseFinish(szError);
	    }
	    else if(m_operate == "didPruchaseCancel"){
	    	_delegate->didPurchaseCancel();
	    }
	   
	}

	void post()
	{
		RunLoop::getInstance()->addObserver(this);
	}

public:

	std::string m_errorCode;

	std::string m_operate;
};

///////////////////////////////////////////////////////////////////////////////
 extern "C" {
JNIEXPORT void JNICALL Java_com_lib_x_PurchaseSDK_didPurchaseFinish(JNIEnv* env, jclass jthis, jstring errorCode)
{
	std::string strError;
    if(errorCode){
	    strError = cocos2d::JniHelper::jstring2string(errorCode);
    }
    PurchaseRunLoopObserver* observer = new PurchaseRunLoopObserver("didPurchaseFinish", strError.c_str());
    observer->post();
}

JNIEXPORT void JNICALL Java_com_lib_x_PurchaseSDK_didPruchaseCancel(JNIEnv* env, jclass jthis, jint from)
{
	PurchaseRunLoopObserver* observer = new PurchaseRunLoopObserver("didPruchaseCancel", "");
	observer->post();
}
}