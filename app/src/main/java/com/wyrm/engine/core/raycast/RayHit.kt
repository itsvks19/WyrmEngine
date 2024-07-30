package com.wyrm.engine.core.raycast

import com.wyrm.engine.core.objects.GameObject
import glm_.vec3.Vec3

data class RayHit(
  val point: Vec3,
  val gameObject: GameObject,
  val t: Float
)