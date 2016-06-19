#ifndef X_SDK_CENTER_H
#define X_SDK_CENTER_H
#include "cocos2d.h"
using namespace cocos2d;

#if CC_TARGET_PLATFORM == CC_PLATFORM_IOS
#include "ios/XAccount.h"
#include "ios/XAnalysis.h"
#include "ios/XPurchase.h"
#include "ios/XGameAnalysis.h"
#include "ios/XShare.h"
#elif CC_TARGET_PLATFORM == CC_PLATFORM_WIN32
#include "win32/XAccount.h"
#include "win32/XAnalysis.h"
#include "win32/XPurchase.h"
#include "win32/XGameAnalysis.h"
#include "win32/XShare.h"
#elif CC_TARGET_PLATFORM == CC_PLATFORM_ANDROID
#include "android/XAccount.h"
#include "android/XAnalysis.h"
#include "android/XPurchase.h"
#include "android/XGameAnalysis.h"
#include "android/XShare.h"
#include "XSDKCenter.h"
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