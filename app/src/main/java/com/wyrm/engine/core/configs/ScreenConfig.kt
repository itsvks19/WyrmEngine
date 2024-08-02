package com.wyrm.engine.core.configs

class ScreenConfig {
  companion object {
    @JvmStatic
    val instance by lazy { ScreenConfig() }
  }

  var glWidth = 0
  var glHeight = 0
}