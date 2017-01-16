#pragma once
#include <map>
#include <string>
using namespace std;

#include <android/log.h>
#include <jni.h>

namespace scx{
class JObject{

public:

    JObject();

    virtual ~JObject();

    void init();

protected:

    jclass m_jclass;

    map<string, jmethodID> m_mapFunction;
};
}