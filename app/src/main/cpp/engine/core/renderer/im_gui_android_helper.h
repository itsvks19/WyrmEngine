//
// Created by Vivek on 8/6/2024.
//

#ifndef WYRMENGINE_IM_GUI_ANDROID_HELPER_H
#define WYRMENGINE_IM_GUI_ANDROID_HELPER_H

#include <android/native_window.h>
#include <jni.h>

#include <string>

class ImGuiAndroidHelper {
private:
  int width;
  int height;

  ANativeWindow* window;

  static void showSoftInput(JNIEnv* env);
  static void hideSoftInput(JNIEnv* env);
  static void pollUnicodeChar(JNIEnv* env);

public:
  void init(ANativeWindow* nativeWindow);
  void mainLoop(JNIEnv* env);
  void surfaceChange(int width, int height);
  void shutdown();
};


#endif//WYRMENGINE_IM_GUI_ANDROID_HELPER_H
