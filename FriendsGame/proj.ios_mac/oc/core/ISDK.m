//
//  NSObject+ISDK.m
//  libscx_objc
//
//  Created by boyi_cs7 on 15/11/18.
//  Copyright © 2015年 boyi_cs7. All rights reserved.
//

#import "ISDK.h"

@interface ISDK()

@property (nonatomic, assign) NSMutableDictionary* otherInfo;

@property (nonatomic, assign) NSMutableDictionary* syncInfo;

@property (nonatomic, assign) NSMutableDictionary* funParams;

@end

@implementation ISDK

static const  char* szEmpty = "";

-(id)init
{
    if(self = [super init])
    {
        self.otherInfo = [[NSMutableDictionary alloc] init];
        self.syncInfo = [[NSMutableDictionary alloc] init];
    }
    return self;
}

-(void)prepareSDK
{
    if(!self.isPrepare)
    {
        self.isPrepare = YES;
        [self prepare];
    }
}

-(void)prepare
{
    
}

-(const char*)getOtherInfo:(const char*)key
{
    NSString* value = (NSString*)[self.otherInfo objectForKey:[NSString stringWithUTF8String:key]];
    const char* szValue = [value UTF8String];
    return szValue == NULL ? szEmpty : szValue;
}

-(void)setOtherInfo:(const char*)key value:(const char*)value
{
    if (key && value)
    {
        NSString* nskey = [NSString stringWithUTF8String:key];
        [self.otherInfo removeObjectForKey:nskey];
        [self.otherInfo  setObject:[NSString stringWithUTF8String:value] forKey:nskey];
    }
}


-(void)callFuntionBegin
{
    if(nil == self.funParams)
        self.funParams = [[NSMutableDictionary alloc] init];
    else
        [self.funParams removeAllObjects];
}

-(void)addFunctionParam:(const char*)key value:(const char*)value
{
    if(self.funParams && key && value)
        [self.funParams  setObject:[NSString stringWithUTF8String:value] forKey:[NSString stringWithUTF8String:key]];
}

-(void)callFunction:(NSString*)name
{
    
}

-(void)callFunctionEnd
{
    if(self.funParams)
        [self.funParams removeAllObjects];
}

-(void)setSyncParam:(NSString*)key value:(NSString*)value
{
    [self.syncInfo setObject:key forKey:value];
}

-(NSString*)getSyncParam:(NSString*)key
{
    return [self.syncInfo objectForKey:key];
}

-(NSDictionary*)getAllSyncParam
{
    return self.syncInfo;
}

-(void)cleanSyncParam
{
    NSArray* allKey = [self.syncInfo allKeys];
    for(NSInteger i = 0; i < [allKey count]; i++)
    {
        NSString* key = [allKey objectAtIndex:i];
        [self.syncInfo removeObjectForKey:key];
    }
    [self.syncInfo removeAllObjects];
}

- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions
{
}

- (void)applicationWillResignActive:(UIApplication *)application
{
    
}

- (void)applicationDidBecomeActive:(UIApplication *)application
{
    
}

- (void)applicationDidEnterBackground:(UIApplication *)application
{
    
}

- (void)applicationWillEnterForeground:(UIApplication *)application
{
    
}

- (void)applicationWillTerminate:(UIApplication *)application
{
    
}

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application
{
    
}

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken
{

}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo
{

}
- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)
                                                                                                                               (UIBackgroundFetchResult))completionHandler
{

}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error
{

}

- (void)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(nullable NSString *)sourceApplication annotation:(id)annotation
{
    
}

- (void)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options
{
    
}

- (void)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{

}

- (UIInterfaceOrientationMask)application:(UIApplication *)application supportedInterfaceOrientationsForWindow:(UIWindow *)window{
    return UIInterfaceOrientationMaskAll;
}
@end
