//
//  LibMain.m
//  libscx_objc
//
//  Created by boyi_cs7 on 15/8/10.
//  Copyright (c) 2015年 boyi_cs7. All rights reserved.
//

#import "LibMain.h"
#import "SDKCenter.h"
#import "AccountSDK.h"
#import "PurchaseSDK.h"


@implementation LibMain

static LibMain* libMain = nil;

+(id)getInstance{
    if(nil == libMain){
        libMain = [[LibMain alloc] init];
    }
    return libMain;
}

+(void)setEAGLView:(UIView*)eaglView;{
    if(libMain.isInit)
        return;
    
    LibMain* libMain = [LibMain getInstance];
    libMain.isInit = YES;
    libMain.eaglView = eaglView;
}


+ (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{
    [[OCSDKCenter  account] application:application didFinishLaunchingWithOptions:launchOptions];
    [[OCSDKCenter  purchase] application:application didFinishLaunchingWithOptions:launchOptions];
}

+(void)applicationWillResignActive:(UIApplication *)application{
    [[OCSDKCenter  account] applicationWillResignActive:application];
    [[OCSDKCenter  purchase] applicationWillResignActive:application];
    
}

+ (void)applicationDidBecomeActive:(UIApplication *)application{
    [[OCSDKCenter  account] applicationDidBecomeActive:application];
    [[OCSDKCenter  purchase] applicationDidBecomeActive:application];
    
}

+ (void)applicationDidEnterBackground:(UIApplication *)application{
    [[OCSDKCenter  account] applicationDidEnterBackground:application ];
    [[OCSDKCenter  purchase] applicationDidEnterBackground:application];
    
}

+ (void)applicationWillEnterForeground:(UIApplication *)application{
    [[OCSDKCenter  account] applicationWillEnterForeground:application ];
    [[OCSDKCenter  purchase] applicationWillEnterForeground:application ];
    
}

+ (void)applicationWillTerminate:(UIApplication *)application{
    [[OCSDKCenter  account] applicationWillTerminate:application ];
    [[OCSDKCenter  purchase] applicationWillTerminate:application ];
   
}

+ (void)applicationDidReceiveMemoryWarning:(UIApplication *)application{
    [[OCSDKCenter  account] applicationDidReceiveMemoryWarning:application ];
    [[OCSDKCenter  purchase] applicationDidReceiveMemoryWarning:application];
}

+ (void)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(nullable NSString *)sourceApplication annotation:(id)annotation
{
    [[OCSDKCenter  account] application:application openURL:url sourceApplication:sourceApplication annotation:annotation];
    [[OCSDKCenter  purchase] application:application openURL:url sourceApplication:sourceApplication annotation:annotation];
    //[[SDKCenter  analysis] application:application openURL:url sourceApplication:sourceApplication annotation:annotation];
    [[OCSDKCenter share] application:application openURL:url sourceApplication:sourceApplication annotation:annotation];
}

+ (void)application:(UIApplication *)app openURL:(NSURL *)url options:(NSDictionary<NSString*, id> *)options
{
    [[OCSDKCenter account] application:app openURL:url options:options];
    [[OCSDKCenter purchase] application:app openURL:url options:options];
    [[OCSDKCenter share] application:app openURL:url options:options];
}

+ (void)application:(UIApplication *)application handleOpenURL:(NSURL *)url
{
    [[OCSDKCenter  account] application:application handleOpenURL:url ];
    [[OCSDKCenter  purchase] application:application handleOpenURL:url ];
    //[[SDKCenter  analysis] application:application openURL:url sourceApplication:sourceApplication annotation:annotation];
    [[OCSDKCenter share] application:application handleOpenURL:url];
}

+(UIInterfaceOrientationMask)application:(UIApplication *)application supportedInterfaceOrientationsForWindow:(UIWindow *)window{
    return [[OCSDKCenter account] application:application supportedInterfaceOrientationsForWindow:window];
}

+(void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {


}

+(void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
    
    

}
+ (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo fetchCompletionHandler:(void (^)
                                                                                                                               (UIBackgroundFetchResult))completionHandler {

}

@end