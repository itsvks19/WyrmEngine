package com.wyrm.engine.graphics.shader

import java.io.Serial
import java.io.Serializable

data class Config(
  val configs: String
) : Serializable {
  companion object {
    @Serial
    const val serialVersionUID = 1L
  }
}