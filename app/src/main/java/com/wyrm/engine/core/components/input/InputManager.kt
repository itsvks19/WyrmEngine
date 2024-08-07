/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.components.input

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import com.wyrm.engine.Time
import com.wyrm.engine.core.components.input.gesture.RotationGestureDetector
import glm_.vec2.Vec2

class InputManager {
  private lateinit var gestureDetector: GestureDetector
  private lateinit var scaleGestureDetector: ScaleGestureDetector
  private lateinit var rotationGestureDetector: RotationGestureDetector

  private val touches: MutableList<Touch> = MutableList(10) { Touch() }
  private val keys: MutableList<Key> = mutableListOf()

  companion object {
    const val SLIDING_DEAD_ZONE = 1f
  }

  fun init(context: Context) {
    initDetectors(context)
  }

  private fun initDetectors(context: Context) {
    gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {

    })

    scaleGestureDetector =
      ScaleGestureDetector(context, object : ScaleGestureDetector.SimpleOnScaleGestureListener() {

      })

    rotationGestureDetector = RotationGestureDetector {

    }
  }

  fun onTouchEvent(event: MotionEvent) {
    gestureDetector.onTouchEvent(event)
    scaleGestureDetector.onTouchEvent(event)
    rotationGestureDetector.onTouchEvent(event)

    val x = event.x
    val y = event.y
    val pointerId = event.getPointerId(event.actionIndex)

    touches[pointerId].position = Vec2(x, y)

    when (event.actionMasked) {
      MotionEvent.ACTION_DOWN, MotionEvent.ACTION_POINTER_DOWN -> {
        handleTouchDown(event)
      }

      MotionEvent.ACTION_UP, MotionEvent.ACTION_POINTER_UP -> {
        handleTouchUp(event)
      }
    }
  }

  private fun handleTouchDown(event: MotionEvent) {
    for (i in 0 until event.pointerCount) {
      val pId = event.getPointerId(i)
      val x2 = event.getX(i)
      val y2 = event.getY(i)

      val oldPos = touches[pId].oldPosition
      if (oldPos.x >= 0f && oldPos.y >= 0f) {
        touches[pId].slide = Vec2(x2 - oldPos.x, y2 - oldPos.y)
      }

      touches[pId].apply {
        oldPosition = Vec2(x2, y2)
        position = Vec2(x2, y2)
        isPressed = true
        isDown = true
        isUp = false
      }
    }
  }

  private fun handleTouchUp(event: MotionEvent) {
    for (i in 0 until event.pointerCount) {
      val pId = event.getPointerId(i)
      touches[pId].apply {
        slide = Vec2(0f, 0f)
        oldPosition = Vec2(-1f, -1f)
        isPressed = false
        isDown = false
        isUp = true
      }
    }
  }

  fun preFrame() {
    touches.forEach {
      it.apply {
        if (isPressed && !isLongPressed) pressedTime += Time.getDeltaTime()
        if (isLongPressed) isLongPressed = false

        if (isPressed && pressedTime >= 3f) isLongPressed = true

        if (isPressed) {
          isUp = false
          isDown = true
        } else {
          isDown = false
          isLongPressed = false
          pressedTime = 0f
          if (!isUp) isSlided = false
          slide = Vec2(0f, 0f)
        }

        if (isDown) isDown = false
        if (isUp) isUp = false

        if (!isSlided && slide.length() >= SLIDING_DEAD_ZONE) {
          isSlided = true
        }
      }
    }
  }

  fun getTouch(index: Int) = touches.getOrNull(index)

  fun getKey(name: String) = keys.find { it.name == name }

  fun setKey(name: String, pressed: Boolean, down: Boolean, up: Boolean) {
    val key = keys.find { it.name == name }
    if (key != null) {
      key.pressed = pressed
      key.down = down
      key.up = up
    } else {
      keys.add(Key(name, pressed, down, up))
    }
  }

  fun registerKey(
    name: String,
    pressed: Boolean = false,
    down: Boolean = false,
    up: Boolean = false
  ) {
    if (getKey(name) == null) {
      keys.add(Key(name, pressed, down, up))
    }
  }
}