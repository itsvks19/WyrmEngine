package com.wyrm.engine.core.renderer

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.GLSurfaceView
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.SimpleOnScaleGestureListener
import com.wyrm.engine.core.Core
import com.wyrm.engine.ext.getCoreInstance

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
  }

  @SuppressLint("ClickableViewAccessibility")
  override fun onTouchEvent(event: MotionEvent): Boolean {
    gestureDetector.onTouchEvent(event)
    scaleGestureDetector.onTouchEvent(event)
    Core.getInstance().onTouchEvent(event, this)
    return true
  }
}