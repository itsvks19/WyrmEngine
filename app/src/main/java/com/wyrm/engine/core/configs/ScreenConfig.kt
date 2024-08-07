/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.configs

class ScreenConfig {
  companion object {
    @JvmStatic
    val instance by lazy { ScreenConfig() }
  }

  var glWidth = 0
  var glHeight = 0
}