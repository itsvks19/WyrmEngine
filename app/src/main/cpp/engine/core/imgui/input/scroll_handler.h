//
// Created by Vivek on 8/7/2024.
//

#ifndef WYRMENGINE_SCROLL_HANDLER_H
#define WYRMENGINE_SCROLL_HANDLER_H

#include <cmath>

#include "imgui.h"
#include "imgui_internal.h"

namespace Input {
  void ScrollWhenDraggingOnVoid(const ImVec2& delta, ImGuiMouseButton mouseButton) {
    ImGuiContext& g     = *ImGui::GetCurrentContext();
    ImGuiWindow* window = g.CurrentWindow;

    bool hovered = false;
    bool held    = false;

    ImGuiID id = window->GetID("##scrolldraggingoverlay");
    ImGui::KeepAliveID(id);

    ImGuiButtonFlags buttonFlags = mouseButton == 0 ? ImGuiButtonFlags_MouseButtonLeft : mouseButton == 1 ? ImGuiButtonFlags_MouseButtonRight
                                                                                                          : ImGuiButtonFlags_MouseButtonMiddle;
    if (g.HoveredId == 0) ImGui::ButtonBehavior(window->Rect(), id, &hovered, &held, buttonFlags);

    float scrollSpeed  = 1.6f;
    float deceleration = 1.2f;

    if (held) {
      ImVec2 delta_copy = delta;

      delta_copy.x *= deceleration;
      delta_copy.y *= deceleration;

      if (delta_copy.x != 0.0f) {
        ImGui::SetScrollX(window, window->Scroll.x + (delta_copy.x * scrollSpeed));
      }
      if (delta_copy.y != 0.0f) {
        ImGui::SetScrollY(window, window->Scroll.y + (delta_copy.y * scrollSpeed));
      }
    }
  }
}// namespace Input

namespace ImGui {
  IMGUI_IMPL_API void MakeScrollableByTouch() {
    ImVec2 mouseDelta = ImGui::GetIO().MouseDelta;

    if (std::fabs(mouseDelta.x) > std::fabs(mouseDelta.y)) {
      // horizontal scrolling
      Input::ScrollWhenDraggingOnVoid(ImVec2(-mouseDelta.x, 0.0f), ImGuiMouseButton_Left);
    } else {
      // vertical scrolling
      Input::ScrollWhenDraggingOnVoid(ImVec2(0.0f, -mouseDelta.y), ImGuiMouseButton_Left);
    }
  }
}// namespace ImGui

#endif//WYRMENGINE_SCROLL_HANDLER_H
