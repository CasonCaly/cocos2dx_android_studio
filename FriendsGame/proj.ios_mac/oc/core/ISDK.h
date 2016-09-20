//
//  NSObject+ISDK.h
//  libscx_objc
//
//  Created by boyi_cs7 on 15/11/18.
//  Copyright © 2015年 boyi_cs7. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>

@interface ISDK : NSObject
{
    
}

@property (nonatomic, assign) BOOL isPrepare;

-(id)init;

#pragma 子类需要复写这个函数,当sdk的各个属性配置好之后,需要调用这个函数做一些初始化的事情

-(void)prepare;

#pragma 以下是功能函数

-(void)prepareSDK;

-(const char*)getOtherInfo:(const char*)key;

-(void)setOtherInfo:(const char*)key value:(const char*)value;

// 从c++调用oc这边的函数
-(void)callFuntionBegin;

-(void)addFunctionParam:(const char*)key value:(const char*)value;

-(void)callFunction:(NSString*)name;

-(void)callFunctionEnd;

// 设置服务器同步的参数

-(void)setSyncParam:(NSString*)key value:(NSString*)value;

-(NSString*)getSyncParam:(NSString*)key;

-(NSDictionary*)getAllSyncParam;

-(void)cleanSyncParam;

#pragma 以下函数都可以被复写

- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;

- (void)applicationWillResignActive:(UIApplication *)application;

- (void)applicationDidBecomeActive:(UIApplication *)application;

- (void)applicationDidEnterBackground:(UIApplication *)application;

- (void)applicationWillEnterForeground:(UIApplication *)application;

- (void)applicationWillTerminate:(UIApplication *)application;

- (void)applicationDidReceiveMemoryWarning:(UIApplication *)application;

- (void)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation;
- (void)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options;

- (void)application:(UIApplication *)application handleOpenURL:(NSURL *)url;

- (UIInterfaceOrientationMask)application:(UIApplication *)application supportedInterfaceOrientationsForWindow:(UIWindow *)window;


// 推送专用
- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken;

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo;

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler;

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error;

@end
