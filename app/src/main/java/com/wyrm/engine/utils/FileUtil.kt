/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.utils

import android.content.Context
import java.io.File
import java.nio.charset.StandardCharsets
import java.nio.file.Path

object FileUtil {
  @JvmStatic
  fun readFromAssets(context: Context, assetsPath: String): String {
    return context.assets.open(assetsPath).bufferedReader(StandardCharsets.UTF_8).readText()
  }

  @JvmStatic
  fun readFromFile(file: File) = file.readText(StandardCharsets.UTF_8)

  @JvmStatic
  fun writeToFile(file: File, content: String) = file.writeText(content, StandardCharsets.UTF_8)

  @JvmStatic
  fun writeToPath(path: String, content: String) = writeToFile(File(path), content)

  @JvmStatic
  fun writeToPath(path: Path, content: String) = writeToFile(path.toFile(), content)
}