#pragma once
#include <map>
#include <string>
using namespace std;

#include <android/log.h>
#include <jni.h>

namespace scx{
class JObject{

public:

    JObject(const string& className);

    virtual ~JObject();

    JObject(const JObject& _object);

    const JObject& operator = (const JObject& _object);

    void callVoidMethod(const sring& name, const string& paramDeclare, ...);

protected:
    int m_refcount;
    jclass m_jclass;
    jobject m_jobject;
    JNIEnv m_env;
    map<string, jmethodID> m_mapFunction;
};
}