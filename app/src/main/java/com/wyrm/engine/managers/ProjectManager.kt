/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

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

  val projects: List<Project>
    get() = File(Constants.PROJECTS_PATH).listFiles()?.map { Project(it) } ?: listOf()

  var openedProject: Project? = null
    private set

  var openedProjectPath: String = ""
    get() = openedProject?.path ?: ""
    private set

  fun openProject(project: Project) {
    openedProject = project
    nativeOpenProject(openedProject!!.path)
  }

  fun closeProject() {
    openedProject = null
    nativeCloseProject()
  }

  fun deleteProject(project: Project): Boolean {
    return FileUtils.delete(project.file)
  }

  private external fun nativeOpenProject(path: String)
  private external fun nativeCloseProject()
}