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

  static void showSoftInput(JNIEnv* env, jobject wyrm_surface);
  static void hideSoftInput(JNIEnv* env, jobject wyrm_surface);
  static void pollUnicodeChar(JNIEnv* env, jobject wyrm_surface);

public:
  void init(ANativeWindow* nativeWindow);
  void mainLoop(JNIEnv* env, jobject wyrm_surface);
  void surfaceChange(int width, int height);
  void shutdown();
};


#endif//WYRMENGINE_IM_GUI_ANDROID_HELPER_H
