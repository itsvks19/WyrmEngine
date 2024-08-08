//
// Created by Vivek on 8/7/2024.
//

#include "wyrm_application.h"

JNIEnv* WyrmApplication::env         = nullptr;
JNIEnv* WyrmApplication::glThreadEnv = nullptr;
jobject WyrmApplication::wyrmSurface = nullptr;

EditorActivity* WyrmApplication::editorActivity = nullptr;

EditorActivity::EditorActivity(JNIEnv* env, jobject activity) : env(env), obj(activity) {}

void WyrmApplication::SetEnv(JNIEnv* env) {
  WyrmApplication::env = env;
}

JNIEnv* WyrmApplication::GetEnv() {
  return env;
}

void WyrmApplication::SetGlThreadEnv(JNIEnv* env) {
  WyrmApplication::glThreadEnv = env;
}

JNIEnv* WyrmApplication::GetGlThreadEnv() {
  return glThreadEnv;
}

void WyrmApplication::SetWyrmSurface(jobject surface) {
  WyrmApplication::wyrmSurface = surface;
}

jobject& WyrmApplication::GetWyrmSurface() {
  return WyrmApplication::wyrmSurface;
}

void WyrmApplication::SetEditorActivity(EditorActivity* activity) {
  WyrmApplication::editorActivity = activity;
}

EditorActivity* WyrmApplication::GetEditorActivity() {
  return WyrmApplication::editorActivity;
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_WyrmApplication_nativeInit(JNIEnv* env, jobject thiz) {
  WyrmApplication::SetEnv(env);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_activities_EditorActivity_nativeInit(JNIEnv* env, jobject thiz, jobject activity) {
  WyrmApplication::SetEditorActivity(new EditorActivity(env, env->NewGlobalRef(activity)));
}