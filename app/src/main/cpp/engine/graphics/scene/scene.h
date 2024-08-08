//
// Created by Vivek on 8/6/2024.
//

#ifndef WYRMENGINE_SCENE_H
#define WYRMENGINE_SCENE_H

#include <string>
#include <utility>

#include "settings/light_settings.h"

class Scene {
private:
  LightSettings lightSettings;

public:
  std::string name;

  explicit Scene(std::string name, LightSettings* lightSettings = new LightSettings());

  LightSettings* getLightSettings() {
    return &this->lightSettings;
  }
};


#endif//WYRMENGINE_SCENE_H
