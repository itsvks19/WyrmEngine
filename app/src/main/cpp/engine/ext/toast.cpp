//
// Created by Vivek on 8/8/2024.
//

#include "toast.h"

#include "wyrm_application.h"

namespace WyrmEngine {
  void Toast::Show(JNIEnv *env, const std::string &message) {
    jclass cls = env->FindClass("com/wyrm/engine/ext/WyrmToast");
    if (cls == nullptr) return;// error: class not found

    jmethodID method = env->GetStaticMethodID(cls, "show", "(Ljava/lang/String;)V");
    if (method == nullptr) return;// error: method not found

    jstring jMessage = env->NewStringUTF(message.c_str());
    env->CallStaticVoidMethod(cls, method, jMessage);
    env->DeleteLocalRef(jMessage);
    env->DeleteLocalRef(cls);
  }

  void Toast::ShowLong(JNIEnv *env, const std::string &message) {
    jclass cls = env->FindClass("com/wyrm/engine/ext/WyrmToast");
    if (cls == nullptr) return;// error: class not found

    jmethodID method = env->GetStaticMethodID(cls, "showLong", "(Ljava/lang/String;)V");
    if (method == nullptr) return;// error: method not found

    jstring jMessage = env->NewStringUTF(message.c_str());
    env->CallStaticVoidMethod(cls, method, jMessage);
    env->DeleteLocalRef(jMessage);
    env->DeleteLocalRef(cls);
  }
}// namespace WyrmEngine