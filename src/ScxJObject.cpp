#include <android/log.h>
#include <jni.h>

extern "C"{

JNIEXPORT jint JNI_OnLoad(JavaVM *vm, void *reserved){
    JniHelper::setJavaVM(vm);
    return JNI_VERSION_1_4;
}

}