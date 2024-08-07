//
// Created by Vivek on 8/6/2024.
//

#include "scene.h"


Scene::Scene(std::string name, LightSettings* lightSettings) : name(std::move(name)), lightSettings(*lightSettings) {
}
