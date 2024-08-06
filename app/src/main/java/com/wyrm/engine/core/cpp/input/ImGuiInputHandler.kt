package com.wyrm.engine.core.cpp.input

import android.os.Build
import android.view.KeyEvent
import android.view.MotionEvent
import androidx.annotation.RequiresApi

object ImGuiInputHandler {
  @JvmStatic
  external fun addCtrlKeyEvent(isDown: Boolean)

  @JvmStatic
  external fun addShiftKeyEvent(isDown: Boolean)

  @JvmStatic
  external fun addAltKeyEvent(isDown: Boolean)

  @JvmStatic
  external fun addSuperKeyEvent(isDown: Boolean)

  @JvmStatic
  external fun addKeyEvent(keyCode: Int, scanCode: Int, isDown: Boolean)

  @JvmStatic
  external fun addMouseSourceMouseEvent()

  @JvmStatic
  external fun addMouseSourceTouchScreenEvent()

  @JvmStatic
  external fun addMouseSourcePenEvent()

  @JvmStatic
  external fun addMousePosEvent(x: Float, y: Float)

  @JvmStatic
  external fun addMouseButtonEvent(button: Int, isDown: Boolean)

  @JvmStatic
  external fun addMouseWheelEvent(xOffset: Float, yOffset: Float)

  @JvmStatic
  external fun wantTextInput(): Boolean

  @JvmStatic
  external fun addInputCharacter(unicodeChar: Int)

  @JvmStatic
  @RequiresApi(Build.VERSION_CODES.S)
  external fun handleKeyInputEvent(keyEvent: KeyEvent)

  @JvmStatic
  @RequiresApi(Build.VERSION_CODES.S)
  external fun handleTouchInputEvent(event: MotionEvent)
}