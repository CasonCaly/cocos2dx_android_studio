//
//  NSObject+AnalysisSDK.m
//  libscx_objc
//
//  Created by boyi_cs7 on 15/11/19.
//  Copyright © 2015年 boyi_cs7. All rights reserved.
//

#import "ShareSDK.h"

@implementation ShareSDK

-(id)init{
    if(self = [super init]){
        
    }
    return self;
}

//分享文字
- (void)shareText:(NSString *)text InScene:(int)scene{
    
}

//分享图片
-(void)shareImage:(const char *)shareImage ThumbImage:(const char *)thumbImage TagName:(NSString *)tagName Description:(NSString *)description Title:(NSString *)title MessageExt:(NSString *)messageExt Action:(NSString *)action InScene:(int)scene ShareType:(int)shareType{
    
}

// 分享音乐
-(void)shareMusicURL:(NSString *)musicURL dataURL:(NSString *)dataURL Title:(NSString *)title Description:(NSString *)description ThumbImage:(const char *)thumbImage InScene:(int)scene{
    
}

// 分享视频
-(void)shareVideoURL:(NSString *)videoURL Title:(NSString *)title Description:(NSString *)description ThumbImage:(const char *)thumbImage InScene:( int)scene{
    
}

// 分享网页
- (void)shareLinkURL:(NSString *)urlString TagName:(NSString *)tagName Title:(NSString *)title Description:(NSString *)description ThumbImage:(const char *)thumbImage InScene:(int)scene{
    
}
@end
