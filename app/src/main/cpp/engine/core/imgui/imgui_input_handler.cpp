//
// Created by Vivek on 8/6/2024.
//

#include <android/api-level.h>
#include <android/input.h>
#include <jni.h>

#include "imgui.h"
#include "imgui_impl_android.h"

#define REQUIRES_API(x) __attribute__((__availability__(android, introduced = x)))
#define API_AT_LEAST(x) __builtin_available(android x, *)

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addCtrlKeyEvent(JNIEnv *env, jclass, jboolean is_down) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddKeyEvent(ImGuiMod_Ctrl, is_down);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addShiftKeyEvent(JNIEnv *env, jclass thiz, jboolean is_down) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddKeyEvent(ImGuiMod_Shift, is_down);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addAltKeyEvent(JNIEnv *env, jclass thiz, jboolean is_down) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddKeyEvent(ImGuiMod_Alt, is_down);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addSuperKeyEvent(JNIEnv *env, jclass thiz, jboolean is_down) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddKeyEvent(ImGuiMod_Super, is_down);
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addKeyEvent(JNIEnv *env, jclass thiz, jint key_code, jint scan_code, jboolean is_down) {
  ImGuiIO &io  = ImGui::GetIO();
  ImGuiKey key = ImGui_ImplAndroid_KeyCodeToImGuiKey(key_code);
  if (key != ImGuiKey_None) {
    io.AddKeyEvent(key, is_down);
    io.SetKeyEventNativeData(key, key_code, scan_code);
  }
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addMouseSourceMouseEvent(JNIEnv *env, jclass thiz) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddMouseSourceEvent(ImGuiMouseSource_Mouse);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addMouseSourceTouchScreenEvent(JNIEnv *env, jclass thiz) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddMouseSourceEvent(ImGuiMouseSource_TouchScreen);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addMouseSourcePenEvent(JNIEnv *env, jclass thiz) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddMouseSourceEvent(ImGuiMouseSource_Pen);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addMousePosEvent(JNIEnv *env, jclass thiz, jfloat x, jfloat y) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddMousePosEvent(x, y);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addMouseButtonEvent(JNIEnv *env, jclass thiz, jint button, jboolean is_down) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddMouseButtonEvent(button, is_down);
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addMouseWheelEvent(JNIEnv *env, jclass thiz, jfloat x_offset, jfloat y_offset) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddMouseWheelEvent(x_offset, y_offset);
}

extern "C" JNIEXPORT jboolean JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_wantTextInput(JNIEnv *env, jclass thiz) {
  ImGuiIO &io = ImGui::GetIO();
  return io.WantTextInput;
}

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_addInputCharacter(JNIEnv *env, jclass thiz, jint unicode_char) {
  ImGuiIO &io = ImGui::GetIO();
  io.AddInputCharacter(unicode_char);
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_handleKeyInputEvent(JNIEnv *env, jclass clazz, jobject key_event) {
  if (API_AT_LEAST(31)) {
    ImGui_ImplAndroid_HandleInputEvent(AKeyEvent_fromJava(env, key_event));
  }
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_core_cpp_input_ImGuiInputHandler_handleTouchInputEvent(JNIEnv *env, jclass clazz, jobject event) {
  if (API_AT_LEAST(31)) {
    ImGui_ImplAndroid_HandleInputEvent(AMotionEvent_fromJava(env, event));
  }
}