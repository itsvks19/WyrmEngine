//
// Created by Vivek on 8/6/2024.
//

#include "im_gui_android_helper.h"

#include <GLES3/gl32.h>

#include <cstring>
#include <filesystem>
#include <fstream>
#include <iostream>

#include "core/imgui/input/scroll_handler.h"
#include "core/imgui/main_menu_bar.h"
#include "ext/toast.h"
#include "graphics/scene/scene.h"
#include "imgui.h"
#include "imgui_impl_android.h"
#include "imgui_impl_opengl3.h"
#include "imgui_internal.h"
#include "imgui_scene_data.h"
#include "managers/project_manager.h"
#include "utils/util.h"
#include "wyrm_application.h"

using namespace WyrmEngine;

static bool g_Initialized = false;
static std::string g_IniFilename;

namespace fs = std::filesystem;

void ImGuiAndroidHelper::init(ANativeWindow *nativeWindow) {
  if (g_Initialized) return;

  this->window = nativeWindow;

  this->width  = ANativeWindow_getWidth(nativeWindow);
  this->height = ANativeWindow_getHeight(nativeWindow);

  static auto sceneFile = ProjectManager::getInstance()->openedProjectPath + "/Scenes/test.wscene";
  fs::path path(sceneFile);

  if (!fs::exists(path.parent_path())) {
    fs::create_directories(path.parent_path());
  }

  // Setup Dear ImGui context
  IMGUI_CHECKVERSION();
  ImGui::CreateContext();
  ImGuiIO &io = ImGui::GetIO();

  // Redirect loading/saving of .ini file to our location.
  g_IniFilename  = Util::configs_dir + "/imgui.ini";
  io.IniFilename = g_IniFilename.c_str();

  // Setup Dear ImGui style
  ImGui::StyleColorsDark();
  //ImGui::StyleColorsLight();

  // Setup Platform/Renderer backends
  ImGui_ImplAndroid_Init(this->window);
  ImGui_ImplOpenGL3_Init("#version 300 es");

  // FIXME: Put some effort into DPI awareness.
  // Important: when calling AddFontFromMemoryTTF(), ownership of font_data is transferred by Dear ImGui by default (deleted is handled by Dear ImGui), unless we set FontDataOwnedByAtlas=false in ImFontConfig
  void *font_data;
  int font_data_size;
  font_data_size = Util::ReadFromAsset("engine/fonts/Roboto-Medium.ttf", &font_data);
  ImFont *font;
  ImFontConfig fontConfig;
  if (fontConfig.SizePixels <= 0.0f) fontConfig.SizePixels = 23.0f;
  if (fontConfig.Name[0] == '\0') {
    ImFormatString(fontConfig.Name, IM_ARRAYSIZE(fontConfig.Name), "Roboto, %dpx", (int) fontConfig.SizePixels);
  }
  font = io.Fonts->AddFontFromMemoryTTF(font_data, font_data_size, 23.0f, &fontConfig);
  IM_ASSERT(font != nullptr);

  io.ConfigFlags |= ImGuiConfigFlags_DockingEnable;

  io.ConfigWindowsMoveFromTitleBarOnly = true;
  // io.ConfigFlags |= ImGuiConfigFlags_ViewportsEnable;

  // Arbitrary scale-up
  // FIXME: Put some effort into DPI awareness
  ImGui::GetStyle().ScaleAllSizes(2.5f);

  ImGuiStyle &style = ImGui::GetStyle();

  style.WindowRounding    = 8.0f;
  style.FrameRounding     = 8.0f;
  style.ChildRounding     = 8.0f;
  style.PopupRounding     = 8.0f;
  style.GrabRounding      = 8.0f;
  style.ScrollbarRounding = 14.0f;
  style.TabRounding       = 10.0f;

  g_Initialized = true;
}

void ImGuiAndroidHelper::mainLoop(JNIEnv *env) {
  ImGuiIO &io = ImGui::GetIO();

  // Poll Unicode characters via JNI
  // FIXME: do not call this every frame because of JNI overhead
  ImGuiAndroidHelper::pollUnicodeChar(env);

  // Open on-screen (soft) input if requested by Dear ImGui
  static bool WantTextInputLast = false;
  if (io.WantTextInput && !WantTextInputLast) {
    ImGuiAndroidHelper::showSoftInput(env);
  } else if (!io.WantTextInput && WantTextInputLast) {
    ImGuiAndroidHelper::hideSoftInput(env);
  }
  WantTextInputLast = io.WantTextInput;

  static auto sceneFile = ProjectManager::getInstance()->openedProjectPath + "/Scenes/test.wscene";
  static bool scene_f   = false;
  fs::path path(sceneFile);

  std::ofstream testScene(sceneFile, std::ios::binary);
  std::ifstream file(sceneFile, std::ios::binary);

  if (!testScene.is_open()) {
    // Toast::Show(WyrmApplication::GetGlThreadEnv(), "error");
    printf("error");
  }
  static Scene scene("TestScene");
  if (fs::exists(path)) {
    file.read(reinterpret_cast<char *>(&scene), sizeof(scene));
  }
  testScene.write(reinterpret_cast<const char *>(&scene), sizeof(scene));

  // Start the Dear ImGui frame
  ImGui_ImplOpenGL3_NewFrame();
  ImGui_ImplAndroid_NewFrame();
  ImGui::NewFrame();

  //// MAIN MENU
  WyrmEngine::CreateMainMenu();
  scene.name = "Hello";
  ////

  //// MAIN MENU FUNCTIONS
  if (SceneMenuData::getInstance()->ShowLightSettings) {
    ImGui::Begin("Light Settings", &SceneMenuData::getInstance()->ShowLightSettings);
    ImGui::ColorEdit3("Space color", (float *) &scene.getLightSettings()->space_color);
    ImGui::ColorEdit3("Ambient color", (float *) &scene.getLightSettings()->ambient_color);

    if (ImGui::Button("Show Toast")) {
      Toast::Show(env, "Hello");
    }
    testScene.write(reinterpret_cast<const char *>(&scene), sizeof(scene));
    testScene.close();
    ImGui::End();
  }
  ////

  ImGui::ShowDemoWindow();
  ImGui::Begin("Dear ImGui Demo");
  ImGui::MakeScrollableByTouch();
  ImGui::End();

  // Rendering
  ImGui::Render();
  {
    glViewport(0, 0, (int) io.DisplaySize.x, (int) io.DisplaySize.y);
    auto clear_color = scene.getLightSettings()->space_color;
    glClearColor(clear_color.x * clear_color.w, clear_color.y * clear_color.w, clear_color.z * clear_color.w, clear_color.w);
    glClear(GL_COLOR_BUFFER_BIT);
  }
  ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}
void ImGuiAndroidHelper::surfaceChange(int width, int height) {
  this->width  = width;
  this->height = height;
  glViewport(0, 0, this->width, this->height);
}
void ImGuiAndroidHelper::showSoftInput(JNIEnv *env) {
  auto wyrm_surface = WyrmApplication::GetWyrmSurface();

  jclass clazz       = env->GetObjectClass(wyrm_surface);
  jmethodID methodId = env->GetMethodID(clazz, "showSoftInput", "()V");
  env->CallVoidMethod(wyrm_surface, methodId);
}
void ImGuiAndroidHelper::hideSoftInput(JNIEnv *env) {
  auto wyrm_surface = WyrmApplication::GetWyrmSurface();

  jclass clazz       = env->GetObjectClass(wyrm_surface);
  jmethodID methodId = env->GetMethodID(clazz, "hideSoftInput", "()V");
  env->CallVoidMethod(wyrm_surface, methodId);
}
void ImGuiAndroidHelper::pollUnicodeChar(JNIEnv *env) {
  auto wyrm_surface = WyrmApplication::GetWyrmSurface();
  auto ref          = env->NewLocalRef(wyrm_surface);

  jclass clazz       = env->GetObjectClass(ref);
  jmethodID methodId = env->GetMethodID(clazz, "pollUnicodeChar", "()I");

  ImGuiIO &io = ImGui::GetIO();
  jint unicode_character;
  while ((unicode_character = env->CallIntMethod(ref, methodId)) != 0)
    io.AddInputCharacter(unicode_character);

  env->DeleteLocalRef(ref);
}

void ImGuiAndroidHelper::shutdown() {
  if (!g_Initialized)
    return;

  // Cleanup
  ImGui_ImplOpenGL3_Shutdown();
  ImGui_ImplAndroid_Shutdown();
  ImGui::DestroyContext();

  g_Initialized = false;
}
