//
// Created by Vivek on 8/6/2024.
//

#ifndef WYRMENGINE_UTIL_H
#define WYRMENGINE_UTIL_H

#include <android/asset_manager_jni.h>

#include <string>

namespace WyrmEngine {
  class Util {
  public:
    static AAssetManager* assetManager;
    static std::string files_dir;
    static std::string configs_dir;
    static std::string imgui_ini_path;

    static int ReadFromAsset(const std::string& filename, void** out_data);
  };

}// namespace WyrmEngine

#endif//WYRMENGINE_UTIL_H
