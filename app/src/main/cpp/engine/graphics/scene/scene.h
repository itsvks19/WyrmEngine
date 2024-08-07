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
  std::string name;
  LightSettings lightSettings;

public:
  explicit Scene(std::string name, LightSettings* lightSettings = new LightSettings());

  std::string getName() {
    return this->name;
  };

  LightSettings* getLightSettings() {
    return &this->lightSettings;
  }
};


#endif//WYRMENGINE_SCENE_H
