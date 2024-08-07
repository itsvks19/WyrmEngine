/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.renderer

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.os.Build
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import com.blankj.utilcode.util.KeyboardUtils
import com.wyrm.engine.core.Core
import com.wyrm.engine.core.cpp.input.ImGuiInputHandler
import com.wyrm.engine.ext.getCoreInstance
import com.wyrm.engine.ext.runOnUiThread
import java.util.concurrent.LinkedBlockingQueue

class WyrmSurface @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
  private val gestureDetector: GestureDetector
  private val scaleGestureDetector: ScaleGestureDetector

  init {
    setEGLContextClientVersion(3)
    setRenderer(WyrmRenderer(this))

    gestureDetector = GestureDetector(context, object : SimpleOnGestureListener() {
      override fun onScroll(
        e1: MotionEvent?,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
      ): Boolean {
        getCoreInstance().onScroll(distanceX, distanceY, width, height)
        return true
      }
    })

    scaleGestureDetector = ScaleGestureDetector(context, object : SimpleOnScaleGestureListener() {
      override fun onScale(detector: ScaleGestureDetector): Boolean {
        getCoreInstance().onScale(detector.scaleFactor)
        return true
      }
    })

    setOnKeyListener { _, keyCode, event ->
      handleKeyInputEvent(keyCode, event)
      true
    }
  }

  override fun onGenericMotionEvent(event: MotionEvent): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      ImGuiInputHandler.handleTouchInputEvent(event)
    } else {
      handleTouchInputForLowerDevices(event)
    }
    return true
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      ImGuiInputHandler.handleTouchInputEvent(event)
    } else {
      handleTouchInputForLowerDevices(event)
    }
    gestureDetector.onTouchEvent(event)
    scaleGestureDetector.onTouchEvent(event)
    Core.getInstance().onTouchEvent(event, this)
    return true
  }

  private fun handleTouchInputForLowerDevices(event: MotionEvent) {
    val action = event.actionMasked
    val pointerIndex = event.actionIndex

    when (event.getToolType(pointerIndex)) {
      MotionEvent.TOOL_TYPE_MOUSE -> ImGuiInputHandler.addMouseSourceMouseEvent()
      MotionEvent.TOOL_TYPE_STYLUS, MotionEvent.TOOL_TYPE_ERASER -> ImGuiInputHandler.addMouseSourcePenEvent()
      MotionEvent.TOOL_TYPE_FINGER, MotionEvent.TOOL_TYPE_UNKNOWN -> ImGuiInputHandler.addMouseSourceTouchScreenEvent()
      else -> ImGuiInputHandler.addMouseSourceTouchScreenEvent()
    }

    when (action) {
      MotionEvent.ACTION_DOWN, MotionEvent.ACTION_UP -> {
        val toolType = event.getToolType(pointerIndex)
        if (toolType == MotionEvent.TOOL_TYPE_FINGER || toolType == MotionEvent.TOOL_TYPE_UNKNOWN) {
          ImGuiInputHandler.addMousePosEvent(event.getX(pointerIndex), event.getY(pointerIndex))
          ImGuiInputHandler.addMouseButtonEvent(0, action == MotionEvent.ACTION_DOWN)
        }
      }

      MotionEvent.ACTION_BUTTON_PRESS, MotionEvent.ACTION_BUTTON_RELEASE -> {
        val buttonState = event.buttonState
        ImGuiInputHandler.addMouseButtonEvent(
          0,
          (buttonState and MotionEvent.BUTTON_PRIMARY) != 0
        )
        ImGuiInputHandler.addMouseButtonEvent(
          1,
          (buttonState and MotionEvent.BUTTON_SECONDARY) != 0
        )
        ImGuiInputHandler.addMouseButtonEvent(
          2,
          (buttonState and MotionEvent.BUTTON_TERTIARY) != 0
        )
      }

      MotionEvent.ACTION_MOVE, MotionEvent.ACTION_HOVER_MOVE -> {
        ImGuiInputHandler.addMousePosEvent(event.getX(pointerIndex), event.getY(pointerIndex))
      }

      MotionEvent.ACTION_SCROLL -> {
        ImGuiInputHandler.addMouseWheelEvent(
          event.getAxisValue(
            MotionEvent.AXIS_HSCROLL,
            pointerIndex
          ),
          event.getAxisValue(
            MotionEvent.AXIS_VSCROLL,
            pointerIndex
          )
        )
      }
    }
  }

  private fun handleKeyInputEvent(keyCode: Int, event: KeyEvent) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      ImGuiInputHandler.handleKeyInputEvent(event)
    } else {
      val scanCode = event.scanCode
      val action = event.action
      val metaState = event.metaState

      ImGuiInputHandler.addCtrlKeyEvent((metaState and KeyEvent.META_CTRL_ON) != 0)
      ImGuiInputHandler.addShiftKeyEvent((metaState and KeyEvent.META_SHIFT_ON) != 0)
      ImGuiInputHandler.addAltKeyEvent((metaState and KeyEvent.META_ALT_ON) != 0)
      ImGuiInputHandler.addSuperKeyEvent((metaState and KeyEvent.META_META_ON) != 0)

      when (action) {
        KeyEvent.ACTION_DOWN, KeyEvent.ACTION_UP -> {
          ImGuiInputHandler.addKeyEvent(keyCode, scanCode, action == KeyEvent.ACTION_DOWN)
        }
      }
    }
  }

  // Queue for the Unicode characters to be polled from native code (via pollUnicodeChar())
  private var unicodeCharacterQueue: LinkedBlockingQueue<Int> = LinkedBlockingQueue()

  override fun dispatchKeyEvent(event: KeyEvent): Boolean {
    if (event.action == KeyEvent.ACTION_DOWN) {
      unicodeCharacterQueue.offer(event.getUnicodeChar(event.metaState))
    }
    handleKeyInputEvent(event.keyCode, event)
    return super.dispatchKeyEvent(event)
  }

  //// THESE FUNCTIONS WILL BE CALLED FROM NATIVE CODE

  private fun showSoftInput() {
    runOnUiThread {
      KeyboardUtils.showSoftInput(this)
    }
  }

  private fun hideSoftInput() {
    runOnUiThread {
      KeyboardUtils.hideSoftInput(this)
    }
  }

  private fun pollUnicodeChar(): Int {
    return unicodeCharacterQueue.poll() ?: 0
  }

  ////
}