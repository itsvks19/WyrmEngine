package com.wyrm.engine

import com.blankj.utilcode.util.PathUtils
import com.wyrm.engine.ext.generateIv
import com.wyrm.engine.ext.generateKey

object Constants {
  const val APP_VERSION = BuildConfig.VERSION_NAME
  const val APP_PACKAGE_NAME = BuildConfig.APPLICATION_ID

  const val ASSET_MARKER = "@@ASSET@@"
  const val COMPILED_MARKER = "@@COMPILED@@"
  const val ENGINE_SHADERS_PATH = "engine/shaders"

  const val DEFAULT_ENCRYPTION_ALGORITHM = "AES"

  @JvmStatic
  val defaultEncryptionKey = generateKey(256)

  @JvmStatic
  val defaultEncryptionIv = generateIv()

  @JvmField
  val FILES_PATH: String = PathUtils.getExternalAppFilesPath()

  @JvmField
  val PROJECTS_PATH = "${FILES_PATH}/Projects"
}