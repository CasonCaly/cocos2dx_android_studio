#ifndef X_SDK_CENTER_H
#define X_SDK_CENTER_H
#include "cocos2d.h"
using namespace cocos2d;

#if CC_TARGET_PLATFORM == CC_PLATFORM_IOS
#include "sdk/ios/XAccount.h"
#include "sdk/ios/XAnalysis.h"
#include "sdk/ios/XPurchase.h"
#include "sdk/ios/XGameAnalysis.h"
#include "sdk/ios/XShare.h"
#include "sdk/XSDKCenter.h"
#elif CC_TARGET_PLATFORM == CC_PLATFORM_WIN32
#include "sdk/win32/XAccount.h"
#include "sdk/win32/XAnalysis.h"
#include "sdk/win32/XPurchase.h"
#include "sdk/win32/XGameAnalysis.h"
#include "sdk/win32/XShare.h"
#include "sdk/XSDKCenter.h"
#elif CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID
#include "sdk/android/XAccount.h"
#include "sdk/android/XAnalysis.h"
#include "sdk/android/XPurchase.h"
#include "sdk/android/XGameAnalysis.h"
#include "sdk/android/XShare.h"
#include "sdk/XSDKCenter.h"
#endif

class SDKCenter : public Ref{
  
public:
    
    static SDKCenter* getInstance();
    
    static Account* getAccount();
    
    static Purchase* getPurchase();
    
	static Analysis* getAnalysis();

	static GameAnalysis* getGameAnalysis();
    
    static Share* getShare();
	
protected:
    
    SDKCenter();
    
    ~SDKCenter();
    
protected:
    
    Account* m_account;
    
    Purchase* m_purchase;
    
    Share* m_share;
	
protected:
    
    static SDKCenter* s_instance;
};

#endif