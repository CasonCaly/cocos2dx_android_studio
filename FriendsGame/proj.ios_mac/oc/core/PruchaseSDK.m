#import "SDKCenter.h"
#import "PurchaseSDK.h"

@implementation PurchaseSDK

@synthesize isDefault;

-(BOOL)getIsDefault
{
    NSString* className = NSStringFromClass([self class]);
    return [className compare:@"PurchaseSDK"] == 0;
}


-(void)dealloc{
    [self clear];
    [super dealloc];
}

-(void)notifyPurchaseFinish:(NSString*)error{
    [self.delegate didPurchaseFinish:error];
}

-(void)startPurchase{
    
}

-(void)clear{
    [self.orderSerial release];
    self.orderSerial = nil;
    
    [self.payid release];
    self.payid = nil;
    
    [self.orderSerial release];
    self.orderSerial = nil;
    
    [self.gameUserServer release];
    self.gameUserServer = nil;
    
    [self.gameUserName release];
    self.gameUserName = nil;
    
    [self.productId release];
    self.productId = nil;
    
    [self.payid release];
    self.payid = nil;
    
    [self.payUrl release];
    self.payUrl = nil;
    
    [self.name release];
    self.name = nil;
    
    [self.delegate release];
    self.delegate = nil;
    self.price = 0.0f;
}

-(NSString*)genOrderId{
    
//    NSDate * date = [NSDate date];
//    NSTimeInterval sec = [date timeIntervalSinceNow];
//    NSDate * currentDate = [NSDate dateWithTimeIntervalSinceNow:sec];
//    NSDateFormatter * dateFormater = [[[NSDateFormatter alloc] init] autorelease];
//    [dateFormater setDateFormat:@"yyyyMMddHHmmss"];
//    
//    AccountSDK* center = [SDKCenter  account];
//
//    
//    NSString * billNO = [NSString stringWithFormat:@"%@|%@|%@|%@|%d",
//                         self.gameUserServer,
//                         self.gameUserId,
//                         center.channelId,
//                         [dateFormater stringFromDate:currentDate],arc4random() % 1000];
//    NSLog(@"%@",billNO);
    
//    self.orderSerial = billNO;
    
    return self.orderSerial;
}

@end
