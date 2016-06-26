#ifndef X_SDK_JNI_HELPER_H
#define X_SDK_JNI_HELPER_H
#include "cocos2d.h"
#include "JniHelper.h"
using namespace cocos2d;

class SdkJniHelper{
public:

	static jobject getAccount();
	
	static jobject getPurchase();
	
	static jobject getAnalysis();
	
	static jobject getShare();
	
public:

	static void prepareSDK(jobject obj);

	static void setXXXWithString(jobject obj, const char* szValue);
	
	static std::string getXXXReturnString(jobject obj, const char* className, const char* functionName);

	static void setOtherInfo(jobject obj, const char* className, const char* key, const char* value);

	static std::string getOtherInfo(jobject obj, const char* className, const char* key);
	
	static void callFuntionBegin(jobject obj, const char* className);
	
	static void addFunctionParam(jobject obj, const char* className, const char* key, const char* value);
	
	static void callFunction(jobject obj, const char* className, const char* name);

	static void callFunctionEnd(jobject obj, const char* className);

	static void setDefaultXXXSDKByClassName(const char* funName, const char* className);
	
	static int getFriendCount(jobject jAccount);
	
	static jobject getFriend(jobject jAccount, int index);
};

#endif