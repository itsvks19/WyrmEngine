package com.wyrm.engine.managers

import com.blankj.utilcode.util.FileUtils
import com.wyrm.engine.Constants
import com.wyrm.engine.ext.toJson
import com.wyrm.engine.model.project.Project
import java.io.File
import java.io.InputStream

class ProjectManager {
  companion object {
    @JvmStatic
    val instance by lazy { ProjectManager() }

    @JvmStatic
    fun createProject(name: String, icon: InputStream): Project {
      val prFile = File("${Constants.PROJECTS_PATH}/$name")
      FileUtils.createOrExistsDir(prFile)
      FileUtils.createOrExistsDir("${prFile.absolutePath}/Scenes")
      FileUtils.createOrExistsDir("${prFile.absolutePath}/Shaders")
      FileUtils.createOrExistsDir("${prFile.absolutePath}/Textures")
      FileUtils.createOrExistsDir("${prFile.absolutePath}/Materials")
      FileUtils.createOrExistsDir("${prFile.absolutePath}/Scripts")

      val iconFile = File("${prFile.absolutePath}/.settings/icon.image")
      FileUtils.createOrExistsDir(iconFile.parentFile)
      FileUtils.createOrExistsFile(iconFile)

      icon.copyTo(iconFile.outputStream())

      val project = Project(prFile)
      File(prFile, ".wproject").writeText(project.toJson())

      return project
    }
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