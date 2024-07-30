package com.wyrm.engine.core.components.console

data class Log @JvmOverloads constructor(
  var type: String,
  var message: String,
  var link: String = ""
)