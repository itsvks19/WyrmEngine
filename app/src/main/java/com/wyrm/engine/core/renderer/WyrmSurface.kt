package com.wyrm.engine.core.renderer

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.MotionEvent
import com.wyrm.engine.core.Core

class WyrmSurface @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null
) : GLSurfaceView(context, attrs) {
  init {
    setEGLContextClientVersion(3)
    setRenderer(WyrmRenderer(context))
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    Core.getInstance().onTouchEvent(event, this)
    return true
  }
}