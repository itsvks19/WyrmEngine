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

public:
  LightSettings lightSettings;
  Scene(std::string name);

  std::string getName() {
    return this->name;
  };
};


#endif//WYRMENGINE_SCENE_H
