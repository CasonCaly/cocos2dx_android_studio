#ifndef SCX_ANALYSIS_H
#define SCX_ANALYSIS_H
#include <string>
#include <map>
#include <vector>
using namespace std;

#include "cocos2d.h"
using namespace cocos2d;


class Analysis : public Ref
{
    
public:
    
    Analysis();
    
    ~Analysis();
    
	void prepare();
	
public:
    
    void trackEvent(const char* event);
    
    void trackEvent(const char* eventId, const char* eventLabel);
    
    void trackEvent(const char* eventId, const char* eventLabel , std::map<std::string, std::string>& parameters);
};


#endif
