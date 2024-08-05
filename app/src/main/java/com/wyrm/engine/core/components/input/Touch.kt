package com.wyrm.engine.core.components.input

import glm_.vec2.Vec2
import java.io.Serializable

class Touch : Serializable {
  var isDown = false
  var isUp = false
  var isPressed = false
  var isLongPressed = false
  var isSlided = false

  @JvmField
  var pressedTime = 0f

  var position = Vec2()
  var oldPosition = Vec2(-1f, -1f)

  var slide = Vec2()

  override fun toString(): String {
    return "isPressed: $isPressed"
  }
}