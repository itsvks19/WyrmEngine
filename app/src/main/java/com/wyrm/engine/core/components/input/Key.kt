package com.wyrm.engine.core.components.input

data class Key @JvmOverloads constructor(
  var name: String,
  var pressed: Boolean = false,
  var down: Boolean = false,
  var up: Boolean = false
)
