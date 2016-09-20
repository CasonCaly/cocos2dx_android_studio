//
//  NSObject_RkFriend.h
//  rkcarddz
//
//  Created by Ruiqi on 15/3/23.
//
//

#import <Foundation/Foundation.h>
#import "Friend.h"

@implementation FriendOC

-(void)dealloc{
    [super dealloc];
    if(self.friendId)
       [self.friendId release];
    
    if(self.gender)
        [self.gender release];
    
    if(self.name)
        [self.name release];
    
    if(self.name)
        [self.profileImage release];
    
    if(self.firstName)
        [self.firstName release];
    
    if(self.lastName)
        [self.lastName release];
    
    if(self.middleName)
        [self.middleName release];
}


@end
