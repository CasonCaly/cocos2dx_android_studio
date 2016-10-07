#include "../XSDKCenter.h"
#include "XSdkJniHelper.h"

#define ShareSDKClass     "com/lib/x/ShareSDK"

Share::Share()
{
}

Share::~Share()
{
}

void Share::prepare()
{
	jobject share = SdkJniHelper::getShare();
	SdkJniHelper::prepareSDK(share);
	JNIEnv* env = JniHelper::getEnv();
	env->DeleteLocalRef(share);
}

void Share::shareText(const char* text, int scene)
{
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, ShareSDKClass, "shareTextInGLThread", "(Ljava/lang/String;I)V"));
		return;
	
	jobject jshare = SdkJniHelper::getShare();
	jstring jtext = nullptr;
	if(text)
		jtext = methodInfo.env->NewStringUTF(text);
	
	methodInfo.env->CallVoidMethod(jshare, methodInfo.methodID, jtext, scene);
	methodInfo.env->DeleteLocalRef(jshare);
	if(jtext)
		methodInfo.env->DeleteLocalRef(jtext);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Share::shareImage(const char*shareImage,const char*thumbImage,const char*tagName,const char*description,const char*title,const char*messageExt,const char*action, int scene, int shareType)
{
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, ShareSDKClass, "shareImageInGLThread", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;II)V"))
		return;
	
	jobject jshare = SdkJniHelper::getShare();
	
	jstring jshareImage = nullptr;
	if(shareImage)
		jshareImage = methodInfo.env->NewStringUTF(shareImage);
	
	jstring jthumbImage = nullptr;
	if(thumbImage)
		jthumbImage = methodInfo.env->NewStringUTF(thumbImage);
	
	jstring jtagName = nullptr;
	if(tagName)
		jtagName = methodInfo.env->NewStringUTF(tagName);
	
	jstring jdescription = nullptr;
	if(description)
		jdescription = methodInfo.env->NewStringUTF(description);
	
	jstring jtitle = nullptr;
	if(title)
		jtitle = methodInfo.env->NewStringUTF(title);
	
	jstring jmessageExt = nullptr;
	if(messageExt)
		jmessageExt = methodInfo.env->NewStringUTF(messageExt);
	
	jstring jaction = nullptr;
	if(action)
		jaction = methodInfo.env->NewStringUTF(action);
	
	methodInfo.env->CallVoidMethod(jshare, methodInfo.methodID, jshareImage, jthumbImage, jtagName, jdescription, jtitle, jmessageExt, jaction, scene, shareType);

	methodInfo.env->DeleteLocalRef(jshare);
	if(jshareImage)
		methodInfo.env->DeleteLocalRef(jshareImage);
	if(jthumbImage)
		methodInfo.env->DeleteLocalRef(jthumbImage);
	if(jtagName)
		methodInfo.env->DeleteLocalRef(jtagName);
	if(jdescription)
		methodInfo.env->DeleteLocalRef(jdescription);
	if(jtitle)
		methodInfo.env->DeleteLocalRef(jtitle);
	if(jmessageExt)
		methodInfo.env->DeleteLocalRef(jmessageExt);
	if(jaction)
		methodInfo.env->DeleteLocalRef(jaction);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Share::shareMusicURL(const char*musicURL,const char*dataURL,const char*description,const char*title,const char*thumbImage,int scene)
{
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, ShareSDKClass, "shareMusicURLInGLThread", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V"))
		return;
	
	jobject jshare = SdkJniHelper::getShare();
	
	jstring jmusicURL = nullptr;
	if(musicURL)
		jmusicURL = methodInfo.env->NewStringUTF(musicURL);
	
	jstring jdataURL = nullptr;
	if(dataURL)
		jdataURL = methodInfo.env->NewStringUTF(dataURL);
	
	jstring jdescription = nullptr;
	if(description)
		jdescription = methodInfo.env->NewStringUTF(description);
	
	jstring jtitle = nullptr;
	if(title)
		jtitle = methodInfo.env->NewStringUTF(title);
	
	jstring jthumbImage = nullptr;
	if(thumbImage)
		jthumbImage = methodInfo.env->NewStringUTF(thumbImage);
	
	methodInfo.env->CallVoidMethod(jshare, methodInfo.methodID, jmusicURL, jdataURL, jdescription, jtitle, jthumbImage, scene);
	methodInfo.env->DeleteLocalRef(jshare);
	if(jmusicURL)
		methodInfo.env->DeleteLocalRef(jmusicURL);
	if(jdataURL)
		methodInfo.env->DeleteLocalRef(jdataURL);
	if(jdescription)
		methodInfo.env->DeleteLocalRef(jdescription);
	if(jtitle)
		methodInfo.env->DeleteLocalRef(jtitle);
	if(jthumbImage)
		methodInfo.env->DeleteLocalRef(jthumbImage);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Share::shareVideoURL(const char*videoURL,const char*description,const char*title,const char*thumbImage,int scene)
{
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, ShareSDKClass, "shareVideoURLInGLThread", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V"))
		return;
	
	jobject jshare = SdkJniHelper::getShare();
	
	jstring jvideoURL = nullptr;
	if(videoURL)
		jvideoURL = methodInfo.env->NewStringUTF(videoURL);
	
	jstring jdescription = nullptr;
	if(description)
		jdescription = methodInfo.env->NewStringUTF(description);
	
	jstring jtitle = nullptr;
	if(title)
		jtitle = methodInfo.env->NewStringUTF(title);
	
	jstring jthumbImage = nullptr;
	if(thumbImage)
		jthumbImage = methodInfo.env->NewStringUTF(thumbImage);
	
	methodInfo.env->CallVoidMethod(jshare, methodInfo.methodID, jvideoURL, jdescription, jtitle, jthumbImage, scene);
	methodInfo.env->DeleteLocalRef(jshare);
	if(jvideoURL)
		methodInfo.env->DeleteLocalRef(jvideoURL);
	if(jdescription)
		methodInfo.env->DeleteLocalRef(jdescription);
	if(jtitle)
		methodInfo.env->DeleteLocalRef(jtitle);
	if(jthumbImage)
		methodInfo.env->DeleteLocalRef(jthumbImage);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}

void Share::shareLinkURL(const char*urlString,const char*tagName,const char*description,const char*title,const char*thumbImage,int scene)
{ 
	JniMethodInfo methodInfo;
    if(!JniHelper::getMethodInfo(methodInfo, "com/lib/x/ShareSDK", "shareLinkURLInGLThread", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;I)V"))
		return;

	jobject jshare = SdkJniHelper::getShare();
	
	jstring jurlString = nullptr;
	if(urlString)
		jurlString = methodInfo.env->NewStringUTF(urlString);
	
	jstring jtagName = nullptr;
	if(tagName)
		jtagName = methodInfo.env->NewStringUTF(tagName);
	
	jstring jdescription = nullptr;
	if(description)
		jdescription = methodInfo.env->NewStringUTF(description);
	
	jstring jtitle = nullptr;
	if(title)
		jtitle = methodInfo.env->NewStringUTF(title);
	
	jstring jthumbImage = nullptr;
	if(thumbImage)
		jthumbImage = methodInfo.env->NewStringUTF(thumbImage);
	
	methodInfo.env->CallVoidMethod(jshare, methodInfo.methodID, jurlString, jtagName, jdescription, jtitle, jthumbImage, scene);

	methodInfo.env->DeleteLocalRef(jshare);
	if(jurlString)
		methodInfo.env->DeleteLocalRef(jurlString);
	if(jtagName)
		methodInfo.env->DeleteLocalRef(jtagName);
	if(jdescription)
		methodInfo.env->DeleteLocalRef(jdescription);
	if(jtitle)
		methodInfo.env->DeleteLocalRef(jtitle);
	if(jthumbImage)
		methodInfo.env->DeleteLocalRef(jthumbImage);
	methodInfo.env->DeleteLocalRef(methodInfo.classID);
}
