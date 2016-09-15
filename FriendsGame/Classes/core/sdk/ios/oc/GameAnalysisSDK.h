//
//  GameAnalysisSDK.h
//  libscx_objc
//
//  Created by boyi_cs7 on 16/1/14.
//  Copyright © 2016年 boyi_cs7. All rights reserved.
//

#import "ISDK.h"

@interface GameAnalysisSDK : ISDK
{
    
}

@property (nonatomic, retain) NSString* appkey;

@property (nonatomic, retain) NSString* channelId;


-(void)setAccountId:(NSString*)accountId;

-(void)setAccountType:(NSString*)accountType;

-(void)setAccountName:(NSString*)accountName;

-(void)setLevel:(NSInteger)level;

-(void)setGameServer:(NSString*)gameServer;

-(void)setGameServerName:(NSString*)gameServerName;

-(void)setGender:(BOOL)isFemal;

-(void)setAge:(NSInteger)age;

-(void)commit;
#pragma

-(void)traceChargeRequest;

-(void)traceChargeSuccess;

-(void)traceReward:(double)virtualCurrencyAmount reason:(NSString*) reason;


-(void)traceGamePurchase:(NSString*)item itemNumber:(NSInteger)itemNumber priceInVirtualCurrency:(double) priceInVirtualCurrency;

-(void)traceGameUse:(NSString*)item itemNumber:(NSInteger)itemNumber;

#pragma

-(void)traceMission:(NSString*) missionId;

-(void)traceMissionCompleted:(NSString*)missionId;

-(void)traceMissionFailed:(NSString*)missionId failedCause:(NSString*)failedCause;

@end
