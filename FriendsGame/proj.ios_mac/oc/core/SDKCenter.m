#import "SDKCenter.h"

@interface SDKInstanceInfo : NSObject{
    ISDK*  m_defaultSDK;
}

@property (nonatomic, assign) NSMutableDictionary*  mapSDK;

@property (nonatomic, assign) ISDK* defaultSDK;

-(id)init;

-(void)addSDK:(ISDK*)sdk;

@end

@implementation SDKInstanceInfo

@synthesize defaultSDK = m_defaultSDK;

-(id)init{
    if(self = [super init]){
        self.mapSDK = [[NSMutableDictionary alloc] init];
    }
    return self;
}

-(void)addSDK:(ISDK*)sdk{
    [self.mapSDK setObject:sdk forKey:NSStringFromClass([sdk class])];
}

-(void)setDefaultSDK:(ISDK *)defaultSDK{
    m_defaultSDK = defaultSDK;
    [self addSDK:defaultSDK];
}

@end


@interface OCSDKCenter()

@property (nonatomic, assign) SDKInstanceInfo* accountInfo;

@property (nonatomic, assign) SDKInstanceInfo* purchaseInfo;

@property (nonatomic, assign) SDKInstanceInfo* analysisInfo;

@property (nonatomic, assign) SDKInstanceInfo* gameAnalysisInfo;

@property (nonatomic, assign) SDKInstanceInfo* pushInfo;

@property (nonatomic, assign) SDKInstanceInfo* shareInfo;

@property (nonatomic, assign) BOOL isPortrait;

@property (nonatomic, assign) BOOL isAutoRotation;

@end

@implementation OCSDKCenter

static OCSDKCenter* s_instance = nil;

-(id)init{
    if(self = [super init]){
        self.accountInfo = [[SDKInstanceInfo alloc] init];
        self.purchaseInfo = [[SDKInstanceInfo alloc] init];
        self.analysisInfo = [[SDKInstanceInfo alloc] init];
        self.gameAnalysisInfo = [[SDKInstanceInfo alloc] init];
        self.pushInfo = [[SDKInstanceInfo alloc] init];
        self.shareInfo = [[SDKInstanceInfo alloc] init];
    }
    return self;
}

+(id)getInstance{
    if(nil == s_instance){
         s_instance = [[OCSDKCenter alloc] init];
        s_instance.isAutoRotation = YES;
    }
    return s_instance;
}

+(void)setISPortrait:(BOOL)isPortrait{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    center.isPortrait = isPortrait;
}

+(void)setIsAutoRotation:(BOOL)autoRotation{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    center.isAutoRotation = autoRotation;
}

+(BOOL)isLandscape{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return !center.isPortrait;
}

+(BOOL)isAutoRotation{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return center.isAutoRotation;
}

+(AccountSDK*)account{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    AccountSDK* sdk = (AccountSDK*)(center.accountInfo.defaultSDK);
    return sdk;
}

+(PurchaseSDK*)purchase{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return (PurchaseSDK*)(center.purchaseInfo.defaultSDK);
}


+(ShareSDK *)share{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return (ShareSDK*)(center.shareInfo.defaultSDK);
}

+(NSDictionary*)allAnalysis{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return center.analysisInfo.mapSDK;
}

+(NSDictionary*)allGameAnalysis{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return center.gameAnalysisInfo.mapSDK;
}

+(NSDictionary*)allPush{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    return center.pushInfo.mapSDK;
}

+(void)setDefaultAccountSDK:(AccountSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    center.accountInfo.defaultSDK = sdk;
}

+(void)addAccountSDK:(AccountSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    [center.accountInfo addSDK:sdk];
}

+(void)setDefaultPurchaseSDK:(PurchaseSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    center.purchaseInfo.defaultSDK = sdk;
}

+(void)addPurchaseSDK:(PurchaseSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    center.purchaseInfo.defaultSDK = sdk;
}

+(void)addAnalysisSDK:(AnalysisSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    [center.analysisInfo addSDK:sdk];
}

+(void)addGameAnalysisSDK:(GameAnalysisSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    [center.gameAnalysisInfo addSDK:sdk];
}

+(void)setDefaultShareSDK:(ShareSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    center.shareInfo.defaultSDK = sdk;
}

+(void)addShareSDK:(ShareSDK*)sdk
{
    OCSDKCenter* center = [OCSDKCenter getInstance];
    [center.shareInfo addSDK:sdk];
}
@end
