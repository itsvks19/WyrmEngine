package com.wyrm.engine.managers

import com.blankj.utilcode.util.FileUtils
import com.wyrm.engine.Constants
import com.wyrm.engine.model.project.Project
import java.io.File

class ProjectManager {
  companion object {
    @JvmStatic
    val instance by lazy { ProjectManager() }
  }

  var openedProject: Project? = null

  fun openProject(project: Project) {
    openedProject = project
  }

  fun closeProject() {
    openedProject = null
  }

  fun deleteProject(project: Project): Boolean {
    return FileUtils.delete(project.file)
  }

  val projects: List<Project>
    get() = File(Constants.PROJECTS_PATH).listFiles()?.map { Project(it) } ?: listOf()
}