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


@interface SDKCenter()

@property (nonatomic, assign) SDKInstanceInfo* accountInfo;

@property (nonatomic, assign) SDKInstanceInfo* purchaseInfo;

@property (nonatomic, assign) SDKInstanceInfo* analysisInfo;

@property (nonatomic, assign) SDKInstanceInfo* gameAnalysisInfo;

@property (nonatomic, assign) SDKInstanceInfo* pushInfo;

@property (nonatomic, assign) SDKInstanceInfo* shareInfo;

@property (nonatomic, assign) BOOL isPortrait;

@property (nonatomic, assign) BOOL isAutoRotation;

@end

@implementation SDKCenter

static SDKCenter* s_instance = nil;

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
         s_instance = [[SDKCenter alloc] init];
        s_instance.isAutoRotation = YES;
    }
    return s_instance;
}

+(void)setISPortrait:(BOOL)isPortrait{
    SDKCenter* center = [SDKCenter getInstance];
    center.isPortrait = isPortrait;
}

+(void)setIsAutoRotation:(BOOL)autoRotation{
    SDKCenter* center = [SDKCenter getInstance];
    center.isAutoRotation = autoRotation;
}

+(BOOL)isLandscape{
    SDKCenter* center = [SDKCenter getInstance];
    return !center.isPortrait;
}

+(BOOL)isAutoRotation{
    SDKCenter* center = [SDKCenter getInstance];
    return center.isAutoRotation;
}

+(AccountSDK*)account{
    SDKCenter* center = [SDKCenter getInstance];
    AccountSDK* sdk = (AccountSDK*)(center.accountInfo.defaultSDK);
    return sdk;
}

+(PurchaseSDK*)purchase{
    SDKCenter* center = [SDKCenter getInstance];
    return (PurchaseSDK*)(center.purchaseInfo.defaultSDK);
}


+(ShareSDK *)share{
    SDKCenter* center = [SDKCenter getInstance];
    return (ShareSDK*)(center.shareInfo.defaultSDK);
}

+(NSDictionary*)allAnalysis{
    SDKCenter* center = [SDKCenter getInstance];
    return center.analysisInfo.mapSDK;
}

+(NSDictionary*)allGameAnalysis{
    SDKCenter* center = [SDKCenter getInstance];
    return center.gameAnalysisInfo.mapSDK;
}

+(NSDictionary*)allPush{
    SDKCenter* center = [SDKCenter getInstance];
    return center.pushInfo.mapSDK;
}

+(void)setDefaultAccountSDK:(AccountSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    center.accountInfo.defaultSDK = sdk;
}

+(void)addAccountSDK:(AccountSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    [center.accountInfo addSDK:sdk];
}

+(void)setDefaultPurchaseSDK:(PurchaseSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    center.purchaseInfo.defaultSDK = sdk;
}

+(void)addPurchaseSDK:(PurchaseSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    center.purchaseInfo.defaultSDK = sdk;
}

+(void)addAnalysisSDK:(AnalysisSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    [center.analysisInfo addSDK:sdk];
}

+(void)addGameAnalysisSDK:(GameAnalysisSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    [center.gameAnalysisInfo addSDK:sdk];
}

+(void)setDefaultShareSDK:(ShareSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    center.shareInfo.defaultSDK = sdk;
}

+(void)addShareSDK:(ShareSDK*)sdk
{
    SDKCenter* center = [SDKCenter getInstance];
    [center.shareInfo addSDK:sdk];
}
@end
