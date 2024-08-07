/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

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
