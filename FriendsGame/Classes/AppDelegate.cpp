#include "AppDelegate.h"
#include "HelloWorldScene.h"


USING_NS_CC;

AppDelegate::AppDelegate() {

}

AppDelegate::~AppDelegate() 
{
}

//if you want a different context,just modify the value of glContextAttrs
//it will takes effect on all platforms
void AppDelegate::initGLContextAttrs()
{
    //set OpenGL context attributions,now can only set six attributions:
    //red,green,blue,alpha,depth,stencil
    GLContextAttrs glContextAttrs = {8, 8, 8, 8, 24, 8};

    GLView::setGLContextAttrs(glContextAttrs);
}

bool AppDelegate::applicationDidFinishLaunching() {
    // initialize director
    auto director = Director::getInstance();
    auto glview = director->getOpenGLView();
    if(!glview) {
        glview = GLViewImpl::createWithRect("HelloCpp", Rect(0, 0, 960, 640));
        director->setOpenGLView(glview);
    }

    director->getOpenGLView()->setDesignResolutionSize(960, 640, ResolutionPolicy::SHOW_ALL);

    // turn on display FPS
    director->setDisplayStats(true);

    // set FPS. the default value is 1.0/60 if you don't call this
    director->setAnimationInterval(1.0 / 60);

    FileUtils::getInstance()->addSearchPath("res");

    // create a scene. it's an autorelease object
    auto scene = HelloWorld::createScene();

    // run
    director->runWithScene(scene);
	CCLOG("applicationDidFinishLaunching");
	Account* account = SDKCenter::getAccount();
	account->prepare();
    account->setDelegate(this);
    account->login();
    bool isDefault = account->isDefault();

	CCLOG("applicationDidFinishLaunching account login  %d", isDefault);
    return true;
}


void AppDelegate::didLoginFinished(const char* error)
{
    if(error)
    {
        CCLOG("didLoginFinished error %s", error);
    }
    else
    {
        Account* account = SDKCenter::getAccount();
        std::string accountId = account->getId();
        std::string firstName = account->getFirstName();
        std::string lastName =account->getLastName();
        std::string profileImage =account->getProfileImage();
        CCLOG("didLoginFinished accountId = %s, firstName = %s, lastName = %s, profileImage = %s ", accountId.c_str(), firstName.c_str(), lastName.c_str(), profileImage.c_str());
    }
}

void AppDelegate::didLoginCancel()
{
    CCLOG("didLoginCancel didLoginCancel");
}

void AppDelegate::didLogoutFinished(AccountLogoutFrom from)
{

}


// This function will be called when the app is inactive. When comes a phone call,it's be invoked too
void AppDelegate::applicationDidEnterBackground() {
    Director::getInstance()->stopAnimation();

    // if you use SimpleAudioEngine, it must be pause
    // SimpleAudioEngine::getInstance()->pauseBackgroundMusic();
}

// this function will be called when the app is active again
void AppDelegate::applicationWillEnterForeground() {
    Director::getInstance()->startAnimation();

    // if you use SimpleAudioEngine, it must resume here
    // SimpleAudioEngine::getInstance()->resumeBackgroundMusic();
}
