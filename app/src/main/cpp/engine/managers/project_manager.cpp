//
// Created by Vivek on 8/7/2024.
//

#include "project_manager.h"

#include <jni.h>

ProjectManager* ProjectManager::instance = nullptr;

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_managers_ProjectManager_nativeOpenProject(JNIEnv* env, jobject thiz, jstring path) {
  ProjectManager::getInstance()->openedProjectPath = env->GetStringUTFChars(path, nullptr);
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_managers_ProjectManager_nativeCloseProject(JNIEnv* env, jobject thiz) {
  ProjectManager::getInstance()->openedProjectPath = "";
}