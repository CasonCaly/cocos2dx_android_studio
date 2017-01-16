#include "ScxJObject.h"

namespace scx{
    
JObject::JObject(const string& className){
    m_jclass = nullptr;
    m_env = nullptr;
    m_jobject = nullptr;
    m_refcount = 1;
}

JObject::~JObject(){
    m_refcount--;
    if(0 == m_refcount){
        if(m_jclass && m_env)
            m_env->DeleteLocalRef(m_jclass);
        if(m_jobject && m_env)
            m_env->DeleteLocalRef(m_jobject);
    }
}

JObject::JObject(const JObject& object){
    if(this == &object)
        return;
    m_refcount = (++object.m_refcount);
    m_jclass = object.m_jclass;
    m_jobject = object.m_jobject;
}

const JObject& JObject::operator = (const JObject& object){
    if(this == &object)
        return *this;
    m_refcount = (++object.m_refcount);
    m_jclass = object.m_jclass;
    m_jobject = object.m_jobject;
}

 void JObject::callVoidMethod(const sring& name, const string& paramDeclare, ...){
     m_env->GetMethodID(name.c_str(), paramDeclare.c_str());
 }

}