#import <Foundation/Foundation.h>
#import "AccountSDK.h"
#import "PurchaseSDK.h"
#import "AnalysisSDK.h"
#import "GameAnalysisSDK.h"
#import "ShareSDK.h"


@interface SDKCenter : NSObject{
    
}

+(id)getInstance;

+(void)setISPortrait:(BOOL)isPortrait;

+(void)setIsAutoRotation:(BOOL)autoRotation;

+(BOOL)isLandscape;

+(BOOL)isAutoRotation;

+(AccountSDK*)account;

+(PurchaseSDK*)purchase;

+(ShareSDK*) share;

+(NSDictionary*)allAnalysis;

+(NSDictionary*)allGameAnalysis;

+(void)setDefaultAccountSDK:(AccountSDK*)sdk;

+(void)addAccountSDK:(AccountSDK*)sdk;

+(void)setDefaultPurchaseSDK:(PurchaseSDK*)sdk;

+(void)addPurchaseSDK:(PurchaseSDK*)sdk;

+(void)addAnalysisSDK:(AnalysisSDK*)sdk;

+(void)addGameAnalysisSDK:(GameAnalysisSDK*)sdk;

+(void)setDefaultShareSDK:(ShareSDK*)sdk;

+(void)addShareSDK:(ShareSDK*)sdk;

@end
