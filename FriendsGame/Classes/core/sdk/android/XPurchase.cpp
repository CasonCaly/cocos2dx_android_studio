#include "XPurchase.h"
#include "XSdkJniHelper.h"

std::string s_temp;
#define PurchaseSDKClass     "com/lib/x/PurchaseSDK"


Purchase::Purchase(){
    m_delegate = nullptr;
}

Purchase::~Purchase(){
    
}

void Purchase::prepare()
{
}

bool Purchase::isDefault()
{
	jobject purchase = SdkJniHelper::getPurchase();
	return SdkJniHelper::isDefault(purchase);
}

void Purchase::startPurchase()
{
	
}

void Purchase::setOrderSerial(const char* orderSerial){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, PurchaseSDKClass, "setOrderSerial", "(Ljava/lang/String;)V"))
		return ;
	
	jobject purchase = SdkJniHelper::getPurchase();
	jstring jOrderSerial = nullptr;
	if(orderSerial)
		jOrderSerial = methodInfo.env->NewStringUTF(orderSerial);
	methodInfo.env->CallVoidMethod(purchase, methodInfo.methodID, jOrderSerial);	 
}

void Purchase::setPrice(float price){
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, PurchaseSDKClass, "setPrice", "(F)V"))
		return ;
	
	jobject purchase = SdkJniHelper::getPurchase();
	methodInfo.env->CallVoidMethod(purchase, methodInfo.methodID, price);	 
}
	
void Purchase::setPayId(const char* payId)
{

}

void Purchase::setGameUserServer(const char* gameUserServer)
{

}

void Purchase::setName(const char* name)
{

}

void Purchase::setGameUserId(const char* userId)
{
}

void Purchase::setGameUserName(const char* userName)
{
}

void Purchase::setPayUrl(const char* payUrl)
{
}

void Purchase::setProductType(const char* szProductType)
{

}

const char* Purchase::getOtherInfo(const char* key){
   	s_temp.clear();
	jobject purchase = SdkJniHelper::getPurchase();
	s_temp = SdkJniHelper::getOtherInfo(purchase, PurchaseSDKClass, key);
	return s_temp.c_str();
}

void Purchase::setOtherInfo(const char* key, const char* value){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::setOtherInfo(purchase, PurchaseSDKClass, key, value);
}

void Purchase::callFuntionBegin(){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::callFuntionBegin(purchase, PurchaseSDKClass);
}

void Purchase::addFunctionParam(const char* key, const char* value){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::addFunctionParam(purchase, PurchaseSDKClass, key, value);
}

void Purchase::callFunction(const char* name){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::callFunction(purchase, PurchaseSDKClass, name);
}

void Purchase::callFunctionEnd(){
	jobject purchase = SdkJniHelper::getPurchase();
	SdkJniHelper::callFunctionEnd(purchase, PurchaseSDKClass);
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
