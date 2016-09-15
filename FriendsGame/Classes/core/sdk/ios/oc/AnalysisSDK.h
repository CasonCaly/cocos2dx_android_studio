//
//  NSObject+AnalysisSDK.h
//  libscx_objc
//
//  Created by boyi_cs7 on 15/11/19.
//  Copyright © 2015年 boyi_cs7. All rights reserved.
//

#import "ISDK.h"

@interface AnalysisSDK : ISDK{
    
}

@property (nonatomic, retain) NSString* appkey;

@property (nonatomic, retain) NSString* channelId;

-(void)trackEvent:(NSString*)event;

-(void)trackEvent:(NSString *)eventId label:(NSString *)eventLabel;

-(void)trackEvent:(NSString *)eventId label:(NSString *)eventLabel parameters:(NSDictionary *)parameters;

@end
