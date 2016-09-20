//
//  LibMain.h
//  libscx_objc
//
//  Created by boyi_cs7 on 15/8/10.
//  Copyright (c) 2015年 boyi_cs7. All rights reserved.
//

#import <Foundation/Foundation.h>
#import <UIKit/UIKit.h>


@interface LibMain : NSObject
{
    
}

@property (nonatomic, assign) BOOL isInit;
@property (nonatomic, assign) UIView* eaglView;

+(void)setEAGLView:(UIView*)eaglView;

+ (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions;

+ (void)applicationWillResignActive:(UIApplication *)application;

+ (void)applicationDidBecomeActive:(UIApplication *)application;

+ (void)applicationDidEnterBackground:(UIApplication *)application;

+ (void)applicationWillEnterForeground:(UIApplication *)application;

+ (void)applicationWillTerminate:(UIApplication *)application;

+ (void)applicationDidReceiveMemoryWarning:(UIApplication *)application;

+ (void)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation;

+(void)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options;

+(void)application:(UIApplication *)application handleOpenURL:(NSURL *)url;

+(UIInterfaceOrientationMask)application:(UIApplication *)application supportedInterfaceOrientationsForWindow:(UIWindow *)window;

// 推送专用
+ (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken;

+(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo;

+ (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)(UIBackgroundFetchResult))completionHandler;


@end