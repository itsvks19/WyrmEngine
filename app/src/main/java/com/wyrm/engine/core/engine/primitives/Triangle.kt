/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.engine.primitives

import glm_.vec3.Vec3

data class Triangle(
  var v0: Vec3,
  var v1: Vec3,
  var v2: Vec3,
  var normal: Vec3,
)