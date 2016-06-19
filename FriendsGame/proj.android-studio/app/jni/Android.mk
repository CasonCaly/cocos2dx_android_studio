LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)

$(call import-add-path,$(LOCAL_PATH)/../../../cocos2d)
$(call import-add-path,$(LOCAL_PATH)/../../../cocos2d/external)
$(call import-add-path,$(LOCAL_PATH)/../../../cocos2d/cocos)

LOCAL_MODULE := cocos2dcpp_shared

LOCAL_MODULE_FILENAME := libcocos2dcpp

LOCAL_SRC_FILES := hellocpp/main.cpp \
              ../../../Classes/HelloWorldScene.cpp \
              ../../../Classes/core/sdk/android/XAccount.cpp \
              ../../../Classes/core/sdk/android/XSdkJniHelper.cpp \
              ../../../Classes/core/sdk/android/XGameAnalysis.cpp \
              ../../../Classes/core/sdk/android/XAnalysis.cpp \
              ../../../Classes/core/sdk/android/XPurchase.cpp \
              ../../../Classes/core/sdk/android/XShare.cpp \
              ../../../Classes/core/sdk/XSDKCenter.cpp \
              ../../../Classes/core/thread/XRunLoop.cpp \
              ../../../Classes/AppDelegate.cpp \

LOCAL_C_INCLUDES := $(LOCAL_PATH)/../../../Classes \
		$(LOCAL_PATH)/../../../cocos2d/cocos/platform/android/jni \
              $(LOCAL_PATH)/../../../Classes/core/sdk \
              $(LOCAL_PATH)/../../../Classes/core/thread \
              $(LOCAL_PATH)/../../../Classes/core/sdk/android \

# _COCOS_HEADER_ANDROID_BEGIN
# _COCOS_HEADER_ANDROID_END


LOCAL_STATIC_LIBRARIES := cocos2dx_static

# _COCOS_LIB_ANDROID_BEGIN
# _COCOS_LIB_ANDROID_END

include $(BUILD_SHARED_LIBRARY)

$(call import-module,.)

# _COCOS_LIB_IMPORT_ANDROID_BEGIN
# _COCOS_LIB_IMPORT_ANDROID_END
