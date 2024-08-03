package com.wyrm.engine.model.project

import com.wyrm.engine.Constants
import java.io.File
import java.io.Serial
import java.io.Serializable

data class Project(
  var file: File
) : Serializable {

  companion object {
    @Serial
    private const val serialVersionUID = 1L
  }

  var name: String = file.name
    get() = file.name
    private set

  var path: String = file.absolutePath
    get() = file.absolutePath
    private set

  val engineVersion by lazy { Constants.APP_VERSION }

  var icon: Icon = Icon(File("$path/.settings/icon.image"))
    private set
    get() = Icon(File("$path/.settings/icon.image"))

  fun rename(newFile: File): Project {
    // Ensure the file rename was successful
    if (file.renameTo(newFile)) {
      file = newFile
      name = newFile.name
      path = newFile.absolutePath
      icon = Icon(File("$path/.settings/icon.image"))
    }

    return Project(newFile)
  }
}
