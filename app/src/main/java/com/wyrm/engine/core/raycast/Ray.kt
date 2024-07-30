package com.wyrm.engine.core.raycast

import glm_.vec3.Vec3

data class Ray @JvmOverloads constructor(
  var origin: Vec3,
  var direction: Vec3,
  var distance: Float = 0f
)