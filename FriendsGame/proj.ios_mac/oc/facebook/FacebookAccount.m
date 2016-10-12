//
//  FacebookAccount.m
//  FriendsGame
//
//  Created by nervecell on 16/9/16.
//
//

#import "FacebookAccount.h"
#import <FBSDKCoreKit/FBSDKCoreKit.h>
#import <FBSDKLoginKit/FBSDKLoginKit.h>
#import <FBSDKCoreKit/FBSDKCoreKit.h>

@interface FacebookAccount(Private)

-(void)accessTokenChanged:(NSNotification *)notification;

-(void)currentProfileChanged:(NSNotification *)notification;

-(void)useGraphLogin;

-(void)getInfoFromJson:(id)dict;

-(void)onFetchMeSuccess;

-(void)fetchFriends;

-(void)parseFriends:(id)dict;

@end

@interface FacebookAccount()

@property (nonatomic, strong) FBSDKAccessToken* token;
@property (nonatomic, strong) FBSDKProfile* profile;

@end


@implementation FacebookAccount

//@synthesize token, profile;

-(id)init{
    if(self = [super init]){
        self.profileImageSize = 120;
        self.needFetchFriends = YES;
    }
    return self;
}

- (void)application:(UIApplication *)application didFinishLaunchingWithOptions:(NSDictionary *)launchOptions{
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(accessTokenChanged:)
                                                 name:FBSDKAccessTokenDidChangeNotification
                                               object:nil];
    [[NSNotificationCenter defaultCenter] addObserver:self
                                             selector:@selector(currentProfileChanged:)
                                                 name:FBSDKProfileDidChangeNotification
                                               object:nil];
 
     [FBSDKProfile enableUpdatesOnAccessTokenChange:YES];
     
     [[FBSDKApplicationDelegate sharedInstance] application:application
                              didFinishLaunchingWithOptions:launchOptions];
    

}

- (void)application:(UIApplication *)application openURL:(NSURL *)url sourceApplication:(NSString *)sourceApplication annotation:(id)annotation{
    [[FBSDKApplicationDelegate sharedInstance] application:application
                                                                  openURL:url
                                                        sourceApplication:sourceApplication
                                                               annotation:annotation
                    ];
    return;
}

-(void)applicationDidBecomeActive:(UIApplication *)application{
    [FBSDKAppEvents activateApp];
}


-(void)login{
    if(self.token){
        [FBSDKAccessToken setCurrentAccessToken:self.token];
        [self useGraphLogin];
    }
    else{
        FBSDKLoginManager *login = [[FBSDKLoginManager alloc] init];
        [login logInWithReadPermissions:nil  fromViewController:nil
                handler:^(FBSDKLoginManagerLoginResult *result, NSError *error) {
                    if (error){
                        [self notifyLoginFinished:error.localizedDescription];
                        return;
                    }
                    
                    if(result.isCancelled){
                        [self notifyLoginCancel];
                        return;
                    }
                    self.token = result.token;
                    [FBSDKAccessToken setCurrentAccessToken:self.token];
                    [self useGraphLogin];
                }
         ];
    }
}

-(void)logout{
    
}

-(void)swtichAccount{
    
}

-(void)accessTokenChanged:(NSNotification *)notification{
    self.token = (FBSDKAccessToken*)notification.userInfo[FBSDKAccessTokenChangeNewKey];
}

-(void)currentProfileChanged:(NSNotification *)notification{
    self.profile = (FBSDKProfile*)notification.userInfo[FBSDKProfileChangeNewKey];
}

-(void)useGraphLogin{
    FBSDKGraphRequest *request = [[FBSDKGraphRequest alloc]
                                 initWithGraphPath:@"me"
                                 parameters:@{@"fields" : @"id,name,gender,first_name,last_name,link"}
                                 ];
    
    [request startWithCompletionHandler:^(FBSDKGraphRequestConnection *connection, id result, NSError *error) {
        if (error) {
            [self notifyLoginFinished:[error localizedFailureReason]];
            return;
        }
        else{
            [self getInfoFromJson:result];
            [self onFetchMeSuccess];
        }
    }];
}

-(void)getInfoFromJson:(id)dict{
    NSDictionary*  nsdict = (NSDictionary*)dict;
    self.accountId = (NSString*)[nsdict objectForKey:@"id"];
    self.firstName = (NSString*)[nsdict objectForKey:@"first_name"];
    self.lastName = (NSString*)[nsdict objectForKey:@"last_name"];
    self.name = (NSString*)[nsdict objectForKey:@"name"];
    self.email = (NSString*)[nsdict objectForKey:@"email"];
    NSURL* url = [self.profile imageURLForPictureMode:FBSDKProfilePictureModeSquare size:CGSizeMake(self.profileImageSize, self.profileImageSize)];
    self.profileImage = [url absoluteString];
}

-(void)onFetchMeSuccess{
    if(self.needFetchFriends){
        [self fetchFriends];
    }
    else{
        [self notifyLoginFinished:nil];
    }
}

-(void)fetchFriends{
    NSString* fileds = [NSString stringWithFormat:@"picture.width(%ld).height(%ld),id,name,gender,first_name,last_name", (long)self.profileImageSize, (long)self.profileImageSize, nil];
    FBSDKGraphRequest* request = [[FBSDKGraphRequest alloc] initWithGraphPath:@"me/friends"
                                                            parameters:@{@"fields" : fileds}
                                  ];
    
    [request startWithCompletionHandler:^(FBSDKGraphRequestConnection *connection, id result, NSError *error) {
        if (error) {
            [self notifyLoginFinished:error.localizedDescription];
            return;
        }
        else{
            [self parseFriends:result];
            [self notifyLoginFinished:nil];
        }
    }];
}

-(void)parseFriends:(id)dict{
    NSDictionary*  nsdict = (NSDictionary*)dict;
    NSArray* dataArray = [nsdict objectForKey:@"data"];
    if(nil == dataArray)
        return;
    
    NSInteger count = [dataArray count];
    for(NSInteger i = 0; i < count; i++){
        NSDictionary* dicFriend = [dataArray objectAtIndex:i];
        FriendOC* friendOC = [[FriendOC alloc] init];
        friendOC.friendId = [dicFriend objectForKey:@"id"];
        friendOC.name = [dicFriend objectForKey:@"name"];
        friendOC.firstName = [dicFriend objectForKey:@"first_name"];
        friendOC.lastName = [dicFriend objectForKey:@"last_name"];
        
        NSDictionary* dicPic = [dicFriend objectForKey:@"picture"];
        NSDictionary* dicData = [dicPic objectForKey:@"data"];
        if(dicData){
            friendOC.profileImage = [dicData objectForKey:@"url"];
        }
        
        [self addFriend:friendOC];
    }
}
@end
