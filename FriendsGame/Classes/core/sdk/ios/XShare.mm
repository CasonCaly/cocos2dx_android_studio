#import "SDKCenter.h"
#include "XShare.h"

Share::Share(){
}

Share::~Share(){
}

void Share::prepare(){
    ShareSDK* shareSDK = (ShareSDK*)[OCSDKCenter share];
    [shareSDK prepareSDK];
}

NSString* safeString2NString(const char* s){
    return s == nil ? @"" : [NSString stringWithUTF8String:s];
}

void Share::shareText(const char* text, int scene){
    ShareSDK* shareSDK = (ShareSDK*)[OCSDKCenter share];
    [shareSDK shareText:safeString2NString(text) InScene:scene];
}

void Share::shareImage(const char*shareImage,const char*thumbImage,const char*tagName,const char*description,const char*title,const char*messageExt,const char*action, int scene, int shareType){
    ShareSDK* shareSDK = (ShareSDK*)[OCSDKCenter share];
    [shareSDK shareImage:shareImage
              ThumbImage:thumbImage
                 TagName:safeString2NString(tagName)
             Description:safeString2NString(description)
                   Title:safeString2NString(title)
              MessageExt:safeString2NString(messageExt)
                  Action:safeString2NString(action)
                 InScene:scene
               ShareType:shareType];
}

void Share::shareMusicURL(const char*musicURL,const char*dataURL,const char*description,const char*title,const char*thumbImage,int scene){
    ShareSDK* shareSDK = (ShareSDK*)[OCSDKCenter share];
    [shareSDK shareMusicURL:safeString2NString(musicURL) dataURL:safeString2NString(dataURL) Title:safeString2NString(title) Description:safeString2NString(description) ThumbImage:thumbImage InScene:scene];
}

void Share::shareVideoURL(const char*videoURL,const char*description,const char*title,const char*thumbImage,int scene){
    ShareSDK* shareSDK = (ShareSDK*)[OCSDKCenter share];
    [shareSDK shareVideoURL:safeString2NString(videoURL) Title:safeString2NString(title) Description:safeString2NString(description) ThumbImage:thumbImage InScene:scene];
}

void Share::shareLinkURL(const char*urlString,const char*tagName,const char*description,const char*title,const char*thumbImage,int scene){
    
    ShareSDK* shareSDK = (ShareSDK*)[OCSDKCenter share];
    [shareSDK shareLinkURL:safeString2NString(urlString) TagName:safeString2NString(tagName) Title:safeString2NString(title) Description:safeString2NString(description) ThumbImage:thumbImage InScene:scene];
}
