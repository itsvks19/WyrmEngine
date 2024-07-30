package com.wyrm.engine.core.engine.primitives

import glm_.vec3.Vec3

data class Triangle(
  var v0: Vec3,
  var v1: Vec3,
  var v2: Vec3,
  var normal: Vec3,
)