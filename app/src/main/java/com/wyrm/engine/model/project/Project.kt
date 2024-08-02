package com.wyrm.engine.model.project

import com.wyrm.engine.Constants
import java.io.File
import java.io.Serializable

data class Project(
  val file: File
) : Serializable {
  val name by lazy { file.name }
  val path by lazy { file.absolutePath }
  val engineVersion by lazy { Constants.APP_VERSION }
  val icon by lazy { Icon(File("$path/icon.image")) }
}
