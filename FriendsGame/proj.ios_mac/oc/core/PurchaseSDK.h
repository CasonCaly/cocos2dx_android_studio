#import "ISDK.h"

#pragma 支付相关

@protocol PurchaseSDKDelegate <NSObject>

-(void)didPurchaseFinish:(NSString*)error;

@end

@interface PurchaseSDK : ISDK
{
}

@property (nonatomic, retain) NSString* payid;

@property (nonatomic, retain) NSString* orderSerial;

@property (nonatomic, assign) CGFloat   price;

@property (nonatomic, retain) NSString* gameUserServer;//游戏服务度名称

@property (nonatomic, retain) NSString* name; // 商品名称

@property (nonatomic, retain) NSString* productId;//商品id

@property (nonatomic, retain) NSString* gameUserId;//游戏用户id

@property (nonatomic, retain) NSString* gameUserName;//游戏用户名

@property (nonatomic, retain) NSString* productType; //比如是不是月卡之类的

@property (nonatomic, assign) id<PurchaseSDKDelegate> delegate;

@property (nonatomic, assign, getter=getIsDefault) BOOL isDefault;

@property (nonatomic, retain) NSString* payUrl;

#pragma 以下是在某项功能完成之后手动调用的

-(void)clear;

-(void)notifyPurchaseFinish:(NSString*)error;

-(NSString *)genOrderId;

#pragma 以下函数都可以被复写

-(void)startPurchase;

@end
