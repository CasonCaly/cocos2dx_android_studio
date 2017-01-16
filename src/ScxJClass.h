#pragma once
#include <map>
#include <string>
using namespace std;

#include <android/log.h>
#include <jni.h>

namespace scx{
class JClass{

public:

    JClass();

    virtual ~JClass();

    void init();

protected:

    map<string, jclass> m_mapClass;
};
}