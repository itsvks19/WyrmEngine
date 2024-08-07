//
// Created by Vivek on 8/6/2024.
//

#include "im_gui_android_helper.h"

#include <GLES3/gl32.h>

#include <cstring>
#include <fstream>

#include "graphics/scene/scene.h"
#include "imgui.h"
#include "imgui_impl_android.h"
#include "imgui_impl_opengl3.h"
#include "imgui_scene_data.h"
#include "utils/util.h"

Scene test_scene("TestScene");

static bool g_Initialized = false;
static std::string g_IniFilename;

void ImGuiAndroidHelper::init(ANativeWindow *nativeWindow) {
  if (g_Initialized) return;

  this->window = nativeWindow;

  this->width  = ANativeWindow_getWidth(nativeWindow);
  this->height = ANativeWindow_getHeight(nativeWindow);

  std::ofstream test(WyrmEngine::Util::files_dir + "/test.wyrm");

  // Setup Dear ImGui context
  IMGUI_CHECKVERSION();
  ImGui::CreateContext();
  ImGuiIO &io = ImGui::GetIO();

  // Redirect loading/saving of .ini file to our location.
  g_IniFilename  = WyrmEngine::Util::configs_dir + "/imgui.ini";
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
  ImFont *font;

  font_data_size = WyrmEngine::Util::ReadFromAsset("engine/fonts/Roboto-Medium.ttf", &font_data);
  font           = io.Fonts->AddFontFromMemoryTTF(font_data, font_data_size, 23.0f);
  IM_ASSERT(font != nullptr);

  io.ConfigFlags |= ImGuiConfigFlags_DockingEnable;
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
void ImGuiAndroidHelper::mainLoop(JNIEnv *env, jobject wyrm_surface) {
  ImGuiIO &io = ImGui::GetIO();

  // Our state
  // (we use static, which essentially makes the variable globals, as a convenience to keep the example code easy to follow)
  static bool show_demo_window    = true;
  static bool show_another_window = false;

  // Poll Unicode characters via JNI
  // FIXME: do not call this every frame because of JNI overhead
  ImGuiAndroidHelper::pollUnicodeChar(env, wyrm_surface);

  // Open on-screen (soft) input if requested by Dear ImGui
  static bool WantTextInputLast = false;
  if (io.WantTextInput && !WantTextInputLast) {
    ImGuiAndroidHelper::showSoftInput(env, wyrm_surface);
  } else if (!io.WantTextInput && WantTextInputLast) {
    ImGuiAndroidHelper::hideSoftInput(env, wyrm_surface);
  }
  WantTextInputLast = io.WantTextInput;

  // Start the Dear ImGui frame
  ImGui_ImplOpenGL3_NewFrame();
  ImGui_ImplAndroid_NewFrame();
  ImGui::NewFrame();

  //// MAIN MENU
  if (ImGui::BeginMainMenuBar()) {
    if (ImGui::BeginMenu("File")) {
      ImGui::EndMenu();
    }
    if (ImGui::BeginMenu("Edit")) {
      ImGui::EndMenu();
    }
    if (ImGui::BeginMenu("View")) {
      ImGui::EndMenu();
    }
    if (ImGui::BeginMenu("Scene")) {
      if (ImGui::MenuItem("Light Settings")) { show_light_settings = true; }
      ImGui::EndMenu();
    }
    if (ImGui::BeginMenu("Code")) {
      ImGui::EndMenu();
    }
    if (ImGui::BeginMenu("Build")) {
      ImGui::EndMenu();
    }
    if (ImGui::BeginMenu("Help")) {
      ImGui::EndMenu();
    }
    ImGui::SameLine(ImGui::GetWindowWidth() - 220);
    ImGui::Text("%.2f FPS (%.2f ms)", io.Framerate, 1000.0f / io.Framerate);
    ImGui::EndMainMenuBar();
  }

  ////

  //// MAIN MENU FUNCTIONS
  if (show_light_settings) {
    ImGui::Begin("Light Settings", &show_light_settings);
    ImGui::ColorEdit3("Space color", (float *) &test_scene.getLightSettings()->space_color);
    ImGui::ColorEdit3("Ambient color", (float *) &test_scene.getLightSettings()->ambient_color);
    ImGui::End();
  }
  ////

  // 1. Show the big demo window (Most of the sample code is in ImGui::ShowDemoWindow()! You can browse its code to learn more about Dear ImGui!).
  if (show_demo_window) {
    ImGui::ShowDemoWindow(&show_demo_window);
  }

  // Rendering
  ImGui::Render();
  glViewport(0, 0, (int) io.DisplaySize.x, (int) io.DisplaySize.y);
  auto clear_color = test_scene.getLightSettings()->space_color;
  glClearColor(clear_color.x * clear_color.w, clear_color.y * clear_color.w, clear_color.z * clear_color.w, clear_color.w);
  glClear(GL_COLOR_BUFFER_BIT);
  ImGui_ImplOpenGL3_RenderDrawData(ImGui::GetDrawData());
}
void ImGuiAndroidHelper::surfaceChange(int width, int height) {
  this->width  = width;
  this->height = height;
  glViewport(0, 0, this->width, this->height);
}
void ImGuiAndroidHelper::showSoftInput(JNIEnv *env, jobject wyrm_surface) {
  jclass clazz       = env->GetObjectClass(wyrm_surface);
  jmethodID methodId = env->GetMethodID(clazz, "showSoftInput", "()V");
  env->CallVoidMethod(wyrm_surface, methodId);
}
void ImGuiAndroidHelper::hideSoftInput(JNIEnv *env, jobject wyrm_surface) {
  jclass clazz       = env->GetObjectClass(wyrm_surface);
  jmethodID methodId = env->GetMethodID(clazz, "hideSoftInput", "()V");
  env->CallVoidMethod(wyrm_surface, methodId);
}
void ImGuiAndroidHelper::pollUnicodeChar(JNIEnv *env, jobject wyrm_surface) {
  jclass clazz       = env->GetObjectClass(wyrm_surface);
  jmethodID methodId = env->GetMethodID(clazz, "pollUnicodeChar", "()I");

  ImGuiIO &io = ImGui::GetIO();
  jint unicode_character;
  while ((unicode_character = env->CallIntMethod(wyrm_surface, methodId)) != 0)
    io.AddInputCharacter(unicode_character);
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
