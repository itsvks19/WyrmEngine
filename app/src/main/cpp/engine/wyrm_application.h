//
// Created by Vivek on 8/7/2024.
//

#ifndef WYRMENGINE_WYRM_APPLICATION_H
#define WYRMENGINE_WYRM_APPLICATION_H

#include <jni.h>

class WyrmApplication {
private:
  static JNIEnv* env;
  static JNIEnv* glThreadEnv;
  static jobject wyrmSurface;

public:
  static void SetEnv(JNIEnv* env);
  static JNIEnv* GetEnv();
  static void SetGlThreadEnv(JNIEnv* env);
  static JNIEnv* GetGlThreadEnv();
  static void SetWyrmSurface(jobject surface);
  static jobject& GetWyrmSurface();
};


#endif//WYRMENGINE_WYRM_APPLICATION_H
