package com.wyrm.engine

import com.blankj.utilcode.util.PathUtils

object Constants {
  const val APP_VERSION = BuildConfig.VERSION_NAME
  const val APP_PACKAGE_NAME = BuildConfig.APPLICATION_ID

  const val ASSET_MARKER = "@@ASSET@@"
  const val COMPILED_MARKER = "@@COMPILED@@"
  const val ENGINE_SHADERS_PATH = "engine/shaders"

  @JvmField
  val FILES_PATH: String = PathUtils.getExternalAppFilesPath()

  @JvmField
  val PROJECTS_PATH = "${FILES_PATH}/Projects"
}