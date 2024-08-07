
//
// Created by Vivek on 8/6/2024.
//

#include <android/native_window_jni.h>
#include <jni.h>

#include "core/renderer/im_gui_android_helper.h"
#include "wyrm_application.h"

static ImGuiAndroidHelper helper;

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_renderer_ImGuiRenderer_init(JNIEnv *env, jclass clazz, jobject surface, jobject wyrm_surface) {
  WyrmApplication::SetGlThreadEnv(env);
  WyrmApplication::SetWyrmSurface(wyrm_surface);
  helper.init(ANativeWindow_fromSurface(env, surface));
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_renderer_ImGuiRenderer_mainLoop(JNIEnv *env, jclass clazz, jobject wyrm_surface) {
  WyrmApplication::SetWyrmSurface(wyrm_surface);
  helper.mainLoop(env);
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_renderer_ImGuiRenderer_surfaceChange(JNIEnv *env, jclass clazz, jint width, jint height) {
  helper.surfaceChange(width, height);
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_renderer_ImGuiRenderer_destroy(JNIEnv *env, jclass clazz) {
  helper.shutdown();
}