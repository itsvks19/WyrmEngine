//
// Created by Vivek on 8/7/2024.
//

#ifndef WYRMENGINE_PROJECT_MANAGER_H
#define WYRMENGINE_PROJECT_MANAGER_H

#include <string>

class ProjectManager {
private:
  static ProjectManager* instance;

  ProjectManager() = default;

public:
  std::string openedProjectPath = "";

  ProjectManager(const ProjectManager* obj) = delete;

  static ProjectManager* getInstance() {
    if (instance == NULL) {
      instance = new ProjectManager();
    }
    return instance;
  }
};


#endif//WYRMENGINE_PROJECT_MANAGER_H
