/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.raycast

import com.wyrm.engine.core.objects.GameObject
import glm_.vec3.Vec3

data class RayHit(
  val point: Vec3,
  val gameObject: GameObject
)