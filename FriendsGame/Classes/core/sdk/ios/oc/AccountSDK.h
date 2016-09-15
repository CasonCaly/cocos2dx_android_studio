#import "ISDK.h"

typedef enum  _AccountSDKToolBarPlace{
    
    SDKToolBarAtTopLeft = 1,   /* 左上 */
    
    SDKToolBarAtTopRight,      /* 右上 */
    
    SDKToolBarAtMiddleLeft,    /* 左中 */
    
    SDKToolBarAtMiddleRight,   /* 右中 */
    
    SDKToolBarAtBottomLeft,    /* 左下 */
    
    SDKToolBarAtBottomRight,   /* 右下 */
    
}AccountSDKToolBarPlace;

typedef enum _LogoutFrom{
    LogoutManual = 1, /* 手动登出 */
    LogoutAuto        /* 自动登出,比如从用户中心登出 */
}LogoutFrom;

#pragma 登录回调相关
@protocol AccountSDKDelegate <NSObject>

-(void)didLoginFinished:(NSString*)error;

-(void)didLogoutFinished:(LogoutFrom)from;

@end

@interface AccountSDK : ISDK{
    
    
}

-(id)init;

#pragma 这些都是sdk配置属性

@property (nonatomic, retain) NSString* channelId;

@property (nonatomic, retain) NSString* channelName;

@property (nonatomic, retain) NSString* appkey;

@property (nonatomic, retain) NSString* appid;

@property (nonatomic, assign) LogoutFrom logoutFrom;

#pragma 这些都是用户属性

@property (nonatomic, retain) NSString* accountId;

@property (nonatomic, retain) NSString* gender;

@property (nonatomic, retain) NSString* name;

@property (nonatomic, retain) NSString* firstName;

@property (nonatomic, retain) NSString* lastName;

@property (nonatomic, retain) NSString* locale;

@property (nonatomic, retain) NSString* email;

@property (nonatomic ,retain) NSString* profileImage;

@property (nonatomic, retain) NSString* sessionId;

@property (nonatomic, assign, readonly, getter=getIsDefault) BOOL isDefault;

@property (nonatomic, assign) id<AccountSDKDelegate> delegate;

@property (nonatomic, assign) BOOL hasUserCenter;

#pragma 以下是在某项功能完成之后手动调用的

-(void)notifyLoginFinished:(NSString*)error;

-(void)notifyLogoutFinished:(LogoutFrom)from;

-(void)clear;

#pragma 以下函数都可以被复写

-(void)login;

-(void)logout;

-(void)swtichAccount;

-(void)gotoUserCetner;

-(void)gotoBBS;

-(void)gotoEnterAPPCetner;

-(void)showToolbar:(BOOL)visible;

-(void)showToolbar:(BOOL)visible withToolBarPlace:(AccountSDKToolBarPlace)place;

@end
