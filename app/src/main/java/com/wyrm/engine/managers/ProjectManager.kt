package com.wyrm.engine.managers

import com.wyrm.engine.Constants
import java.io.File

class ProjectManager {
  companion object {
    @JvmStatic
    val instance by lazy { ProjectManager() }
  }

  var loadedProjectName = ""
    private set

  val loadedProjectLocation: String
    get() {
      return if (loadedProjectName == Constants.COMPILED_MARKER) {
        "${Constants.ASSET_MARKER}/compiled"
      } else {
        "${Constants.PROJECTS_PATH}/$loadedProjectName"
      }
    }

  val projects: List<File>
    get() = File(Constants.PROJECTS_PATH).listFiles()
      ?.filter { it.isDirectory }
      ?: listOf()

  fun loadProject(projectName: String) {
    loadedProjectName = projectName
  }
}