package com.wyrm.engine.model.project

import java.io.File
import java.io.Serial
import java.io.Serializable

data class Icon(
  val file: File
) : Serializable {
  companion object {
    @Serial
    private const val serialVersionUID = 1L
  }

  val path: String get() = file.absolutePath
  val isExists get() = file.exists()
}
