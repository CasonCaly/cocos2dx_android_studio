#import "SDKCenter.h"
#import "AccountSDK.h"

@implementation AccountSDK

-(id)init{
    if(self = [super init]){
        self.logoutFrom = LogoutAuto;
        self.channelId = @"100";
    }
    return self;
}

-(BOOL)getIsDefault{
    NSString* className = NSStringFromClass([self class]);
    return [className compare:@"AccountSDK"] == 0;
}


-(void)dealloc{
    [self clear];
    [super dealloc];
}


-(void)notifyLoginFinished:(NSString*)error{
    [self.delegate didLoginFinished:error];
}

-(void)notifyLogoutFinished:(LogoutFrom)from{
    [self.delegate didLogoutFinished:from];
}

-(void)clear{
   [self.channelId release];
   self.channelId = nil;
    
   [self.channelName release];
   self.channelName = nil;
    
   [self.appkey release];
   self.appkey = nil;

   [self.appid release];
   self.appid = nil;
    
   [self.accountId release];
   self.accountId = nil;
   [self.gender release];
   self.gender = nil;
   [self.name release];
   self.name = nil;
   [self.firstName release];
   self.name = nil;
   [self.lastName release];
   self.lastName = nil;
   [self.locale release];
   self.locale = nil;
   [self.email release];
   self.email = nil;
   [self.profileImage release];
   self.profileImage = nil;
   [self.sessionId release];
   self.sessionId = nil;
   [self.delegate release];
   self.delegate = nil;
    self.logoutFrom = LogoutAuto;
}

#pragma 以下函数都可以被复写
-(void)login{
}

-(void)logout{
    [self notifyLogoutFinished:self.logoutFrom];
}

-(void)swtichAccount{
}

-(void)gotoUserCetner{
}

-(void)gotoBBS{
}

-(void)gotoEnterAPPCetner{
}

-(void)showToolbar:(BOOL)visible{
}

-(void)showToolbar:(BOOL)visible withToolBarPlace:(AccountSDKToolBarPlace)place{
}
@end
