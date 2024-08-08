//
// Created by Vivek on 8/7/2024.
//

#ifndef WYRMENGINE_TOAST_H
#define WYRMENGINE_TOAST_H

#include <jni.h>

#include <string>

namespace WyrmEngine {
  struct Toast {
    static void Show(JNIEnv* env, const std::string& message);
    static void ShowLong(JNIEnv* env, const std::string& message);
  };
}// namespace WyrmEngine

#endif//WYRMENGINE_TOAST_H
