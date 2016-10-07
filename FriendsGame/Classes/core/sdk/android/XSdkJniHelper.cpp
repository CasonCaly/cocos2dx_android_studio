#include "XSdkJniHelper.h"

#define SDKCenterClass  "com/lib/x/SDKCenter"
#define ISDKClass "com/lib/x/ISDK"
#define AccountSDKClassReturn "()Lcom/lib/x/AccountSDK;"
#define PurchaseSDKClassReturn "()Lcom/lib/x/PurchaseSDK;"
#define AnalysisSDKClassReturn "()Lcom/lib/x/AnalysisSDK;"
#define ShareSDKClassReturn "()Lcom/lib/x/ShareSDK;"

#define AccountSDKClass "com/lib/x/AccountSDK"
#define AccountFriendClass     "com/lib/x/AccountSDK$Friend"
#define AccountFriendClassReturn     "(I)Lcom/lib/x/AccountSDK$Friend;"

jobject SdkJniHelper::getAccount(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getStaticMethodInfo(methodInfo, SDKCenterClass, "account", AccountSDKClassReturn))
    {
		return nullptr;
	}
	return methodInfo.env->CallStaticObjectMethod(methodInfo.classID, methodInfo.methodID);
}
	
jobject SdkJniHelper::getPurchase(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getStaticMethodInfo(methodInfo, SDKCenterClass, "purchase", PurchaseSDKClassReturn))
		return nullptr;	
	return methodInfo.env->CallStaticObjectMethod(methodInfo.classID, methodInfo.methodID);
}
	
jobject SdkJniHelper::getAnalysis(){
	JniMethodInfo methodInfo;
    if(!JniHelper::getStaticMethodInfo(methodInfo, SDKCenterClass, "analysis", AnalysisSDKClassReturn))
		return nullptr;	
	return methodInfo.env->CallStaticObjectMethod(methodInfo.classID, methodInfo.methodID);
}
	
jobject SdkJniHelper::getShare()
{
	JniMethodInfo methodInfo;
    if(!JniHelper::getStaticMethodInfo(methodInfo, SDKCenterClass, "share", ShareSDKClassReturn))
		return nullptr;	
	return methodInfo.env->CallStaticObjectMethod(methodInfo.classID, methodInfo.methodID);
}
	
bool SdkJniHelper::isDefault(jobject jsdk)
{
	if(nullptr == jsdk)
		return false;

	JNIEnv* env = JniHelper::getEnv();
	jclass sdkClass = env->GetObjectClass(jsdk);
 	jmethodID methodID = env->GetMethodID(sdkClass, "isDefault","()Z");
 	if(methodID == nullptr)
 		return false;

	bool isDefault = env->CallBooleanMethod(jsdk, methodID);
	env->DeleteLocalRef(sdkClass);
	return isDefault;
}

void SdkJniHelper::setXXXWithString(jobject obj, const char* className, const char* functionName, const char* szValue){
	if(nullptr == obj)
		return;
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, functionName, "(Ljava/lang/String;)V"))
		return ;
	
	jstring jValue = nullptr;
	if(szValue)
		jValue = methodInfo.env->NewStringUTF(szValue);

	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID, jValue);
	if(jValue)
		methodInfo.env->DeleteLocalRef(jValue);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void SdkJniHelper::prepareSDK(jobject obj){
	if(nullptr == obj)
		return;
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, ISDKClass, "prepareSDK", "()V"))
		return;	
	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}
	
std::string SdkJniHelper::getXXXReturnString(jobject obj, const char* className, const char* functionName){
	if(nullptr == obj)
		return "";
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, functionName, "()Ljava/lang/String;"))
		return "";
	jstring ret = (jstring)methodInfo.env->CallObjectMethod(obj, methodInfo.methodID);
	std::string str = JniHelper::jstring2string(ret);
	if(ret)
		methodInfo.env->DeleteLocalRef(ret);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
	return str;
}

void SdkJniHelper::setOtherInfo(jobject obj, const char* className, const char* key, const char* value){
	if(nullptr == obj)
		return;
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, "setOtherInfo", "(Ljava/lang/String;Ljava/lang/String;)V"))
		return;
	jstring jKey = nullptr;
	jstring jValue = nullptr;
	if(key)
		jKey = methodInfo.env->NewStringUTF(key);
	if(value)
		jValue = methodInfo.env->NewStringUTF(value);
	
	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID, jKey, jValue);
	if(jKey)
		methodInfo.env->DeleteLocalRef(jKey);
	if(jValue)
		methodInfo.env->DeleteLocalRef(jValue);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

std::string SdkJniHelper::getOtherInfo(jobject obj, const char* className, const char* key){
	if(nullptr == obj)
		return "";
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, "getOtherInfo", "(Ljava/lang/String;)Ljava/lang/String;"))
		return "";
	jstring jKey = nullptr;
	if(key)
		jKey = methodInfo.env->NewStringUTF(key);
	jstring jValue = (jstring)methodInfo.env->CallObjectMethod(obj, methodInfo.methodID, jKey);	
	std::string strValue = JniHelper::jstring2string(jValue);

	if(jKey)
		methodInfo.env->DeleteLocalRef(jKey);
	if(jValue)
		methodInfo.env->DeleteLocalRef(jValue);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
	return strValue;	
}

void SdkJniHelper::callFuntionBegin(jobject obj, const char* className){
	if(nullptr == obj)
		return;
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, "callFuntionBeginInGLThread", "()V"))
		return;
	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}
	
void SdkJniHelper::addFunctionParam(jobject obj, const char* className, const char* key, const char* value){
	if(nullptr == obj)
		return;
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, "addFunctionParamInGLThread", "(Ljava/lang/String;Ljava/lang/String;)V"))
		return;
	jstring jKey = nullptr;
	jstring jValue = nullptr;
	if(key)
		jKey = methodInfo.env->NewStringUTF(key);
	if(value)
		jValue = methodInfo.env->NewStringUTF(value);
	
	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID, jKey, jValue);	
	if(jKey)
		methodInfo.env->DeleteLocalRef(jKey);
	if(jValue)
		methodInfo.env->DeleteLocalRef(jValue);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}
	
void SdkJniHelper::callFunction(jobject obj, const char* className, const char* name){
	if(nullptr == obj)
		return;
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, "callFunctionInGLThread", "(Ljava/lang/String;)V"))
		return;
	jstring jName = nullptr;
	if(name)
		jName = methodInfo.env->NewStringUTF(name);
	
	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID, jName);	

	if(jName)
		methodInfo.env->DeleteLocalRef(jName);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void SdkJniHelper::callFunctionEnd(jobject obj, const char* className){
	if(nullptr == obj)
		return;
	
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, className, "callFunctionEndInGLThread", "()V"))
		return;
	
	methodInfo.env->CallVoidMethod(obj, methodInfo.methodID);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);	
}

void SdkJniHelper::setDefaultXXXSDKByClassName(const char* funName, const char* className){
	JniMethodInfo methodInfo;
    if(!JniHelper::getStaticMethodInfo(methodInfo, SDKCenterClass, funName, "(Ljava/lang/String;)V"))
		return;
	jstring jName = nullptr;
	if(className)
	{
		std::string strClassName = className;
		std::string::size_type pos = 0;  
	    std::string::size_type srcLen = 1;  
	    std::string::size_type desLen = 1;  
	    pos = strClassName.find("/", pos);   
	    while ((pos != std::string::npos))  
	    {  
	        strClassName.replace(pos, 1, ".");  
	        pos = strClassName.find("/", (pos+desLen));  
	    }  
		jName = methodInfo.env->NewStringUTF(strClassName.c_str());
	}
	methodInfo.env->CallStaticVoidMethod(methodInfo.classID, methodInfo.methodID, jName);
	if(jName)
		methodInfo.env->DeleteLocalRef(jName);
}

int SdkJniHelper::getFriendCount(jobject jAccount)
{
	JniMethodInfo methodInfo;
	if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "getFriendCount", "()I"))
		return 0;
	int count = methodInfo.env->CallIntMethod(jAccount, methodInfo.methodID);

	methodInfo.env->DeleteLocalRef(methodInfo.classID);
	return count;
}

jobject SdkJniHelper::getFriend(jobject jAccount, int index)
{
	JniMethodInfo methodInfo;
	if(!JniHelper::getMethodInfo(methodInfo, AccountSDKClass, "getFriend", AccountFriendClassReturn))
		return nullptr;
	jobject jfriend = methodInfo.env->CallObjectMethod(jAccount, methodInfo.methodID, index);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
	return jfriend;
}