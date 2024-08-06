//
// Created by Vivek on 8/6/2024.
//

#include "util.h"

#include "imgui.h"

namespace WyrmEngine {

  AAssetManager *Util::assetManager = nullptr;
  std::string Util::files_dir       = "";
  std::string Util::imgui_ini_path  = "";
  std::string Util::configs_dir     = files_dir + "/.configs";

  int Util::ReadFromAsset(const std::string &filename, void **out_data) {
    int num_bytes = 0;
    AAsset *asset = AAssetManager_open(assetManager, filename.c_str(), AASSET_MODE_BUFFER);
    if (asset) {
      num_bytes              = (int) AAsset_getLength(asset);
      *out_data              = IM_ALLOC(num_bytes);
      int64_t num_bytes_read = AAsset_read(asset, *out_data, num_bytes);
      AAsset_close(asset);
      IM_ASSERT(num_bytes_read == num_bytes);
    }
    return num_bytes;
  }
}// namespace WyrmEngine

extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_WyrmApplication_setAssets(JNIEnv *env, jobject thiz, jobject assets) {
  WyrmEngine::Util::assetManager = AAssetManager_fromJava(env, assets);
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_WyrmApplication_setExternalFilesDir(JNIEnv *env, jobject thiz, jstring path) {
  WyrmEngine::Util::files_dir   = env->GetStringUTFChars(path, nullptr);
  WyrmEngine::Util::configs_dir = WyrmEngine::Util::files_dir + "/.configs";
}
extern "C" JNIEXPORT void JNICALL
Java_com_wyrm_engine_WyrmApplication_setImGuiIniPath(JNIEnv *env, jobject thiz, jstring path) {
  WyrmEngine::Util::imgui_ini_path = env->GetStringUTFChars(path, nullptr);
}