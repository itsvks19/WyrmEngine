package com.wyrm.engine.core.engine

import glm_.vec3.Vec3

class Physics {
  companion object {
    @JvmStatic
    val gravity = Vec3(0f, -9.81f, 0f)
  }
}