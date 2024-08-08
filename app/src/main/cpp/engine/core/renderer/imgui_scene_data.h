//
// Created by Vivek on 8/6/2024.
//

#ifndef WYRMENGINE_IMGUI_SCENE_DATA_H
#define WYRMENGINE_IMGUI_SCENE_DATA_H

namespace WyrmEngine {
  class SceneMenuData {
    static SceneMenuData* instance;

  public:
    bool ShowLightSettings = false;

    static SceneMenuData* getInstance() {
      if (instance == nullptr) {
        instance = new WyrmEngine::SceneMenuData();
      }
      return instance;
    }
  };
}// namespace WyrmEngine

#endif//WYRMENGINE_IMGUI_SCENE_DATA_H
