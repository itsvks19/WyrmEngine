//
// Created by Vivek on 8/6/2024.
//

#ifndef WYRMENGINE_LIGHT_SETTINGS_H
#define WYRMENGINE_LIGHT_SETTINGS_H

#include "imgui.h"

class LightSettings {
private:
public:
  ImVec4 space_color   = ImVec4(0.53f, 0.81f, 0.92f, 1.0f);
  ImVec4 ambient_color = ImVec4(0.02f, 0.02f, 0.08f, 1.0f);
};


#endif//WYRMENGINE_LIGHT_SETTINGS_H
