//
// Created by Vivek on 8/8/2024.
//

#include "main_menu_bar.h"

#include "core/renderer/imgui_scene_data.h"
#include "ext/toast.h"
#include "imgui.h"
#include "wyrm_application.h"

void CloseEditor();

namespace WyrmEngine {
  void CreateMainMenu() {
    ImGuiIO& io = ImGui::GetIO();

    if (ImGui::BeginMainMenuBar()) {
      if (ImGui::BeginMenu("File")) {
        ShowFileMenu();
        ImGui::EndMenu();
      }
      if (ImGui::BeginMenu("Edit")) {
        ImGui::EndMenu();
      }
      if (ImGui::BeginMenu("View")) {
        ImGui::EndMenu();
      }
      if (ImGui::BeginMenu("Scene")) {
        if (ImGui::MenuItem("Light Settings")) { SceneMenuData::getInstance()->ShowLightSettings = true; }
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
  }

  void ShowFileMenu() {
    if (ImGui::MenuItem("New")) {
      Toast::Show(WyrmApplication::GetGlThreadEnv(), "New");
    }
    if (ImGui::MenuItem("Open", "Ctrl+O")) {
      Toast::Show(WyrmApplication::GetGlThreadEnv(), "Open");
    }
    if (ImGui::BeginMenu("Open Recent")) {
      ImGui::MenuItem("fish_hat.c");
      ImGui::MenuItem("fish_hat.inl");
      ImGui::MenuItem("fish_hat.h");
      ImGui::EndMenu();
    }
    if (ImGui::MenuItem("Save", "Ctrl+S")) {
      Toast::Show(WyrmApplication::GetGlThreadEnv(), "Save");
    }
    if (ImGui::MenuItem("Save As..")) {
      Toast::Show(WyrmApplication::GetGlThreadEnv(), "Save As..");
    }

    ImGui::Separator();
    if (ImGui::BeginMenu("Options")) {
      static bool enabled = true;
      ImGui::MenuItem("Enabled", "", &enabled);
      ImGui::EndMenu();
    }

    ImGui::Separator();
    if (ImGui::MenuItem("Quit", "Alt+F4")) {
      CloseEditor();
    }
  }
}// namespace WyrmEngine

void CloseEditor() {
  auto editor = WyrmApplication::GetEditorActivity();
  JNIEnv* env = WyrmApplication::GetGlThreadEnv();
  auto cls    = env->GetObjectClass(editor->obj);
  auto method = env->GetMethodID(cls, "closeEditor", "()V");
  env->CallVoidMethod(editor->obj, method);
}
