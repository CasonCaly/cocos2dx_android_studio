#ifndef X_SHARE_H
#define X_SHARE_H
#include <string>
#include <map>
#include <vector>
using namespace std;
#include "cocos2d.h"
using namespace cocos2d;

class Share : public Ref
{
    
public:
    
    Share();
    
    ~Share();
    
    void prepare();
	
public:

    void shareText(const char*text, int scene);
    
    void shareImage(const char*shareImage,const char*thumbImage,const char*tagName,const char*description,const char*title,const char*messageExt,const char*action, int scene, int shareType);
    
    void shareMusicURL(const char*musicURL,const char*dataURL,const char*description,const char*title,const char*thumbImage,int scene);
    
    void shareVideoURL(const char*videoURL,const char*description,const char*title,const char*thumbImage,int scene);
    
    void shareLinkURL(const char*urlString,const char*tagName,const char*description,const char*title,const char*thumbImage,int scene);
};

#endif
