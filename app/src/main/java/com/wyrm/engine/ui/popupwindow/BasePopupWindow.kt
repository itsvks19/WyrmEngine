/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.ui.popupwindow

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.transition.Fade
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.annotation.GravityInt
import androidx.viewbinding.ViewBinding
import com.wyrm.engine.ext.applyDefaultBackgroundForPopupWindow
import com.wyrm.engine.ext.dp

abstract class BasePopupWindow<T : ViewBinding>(
  internal val context: Context,
  private val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
) {
  protected val binding: T by lazy {
    bindingInflater.invoke(
      LayoutInflater.from(context),
      null,
      false
    )
  }
  private val popupWindow = PopupWindow(
    binding.root,
    WindowManager.LayoutParams.WRAP_CONTENT,
    WindowManager.LayoutParams.WRAP_CONTENT
  ).apply {
    isFocusable = true
    elevation = 10f.dp.toFloat()
    isClippingEnabled = false
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    animationStyle = 0
    isOutsideTouchable = false
    enterTransition = Fade(Fade.IN)
    exitTransition = Fade(Fade.OUT)
  }

  init {
    binding.root.applyDefaultBackgroundForPopupWindow()
  }

  /**
   * Call this method from constructor of derived class
   */
  abstract fun setupViews()

  fun dismiss() {
    popupWindow.dismiss()
  }

  @JvmOverloads
  fun showAsDropDown(
    anchor: View,
    xoff: Int = 0,
    yoff: Int = 0,
    @GravityInt gravity: Int = Gravity.TOP or Gravity.CENTER
  ) {
    popupWindow.showAsDropDown(anchor, xoff, yoff, gravity)
  }

  @JvmOverloads
  fun showAtLocation(
    view: View,
    @GravityInt gravity: Int = Gravity.TOP or Gravity.CENTER,
    x: Int = 0,
    y: Int = 0
  ) {
    popupWindow.showAtLocation(view, gravity, x, y)
  }

  @JvmOverloads
  fun update(x: Int, y: Int, width: Int = -1, height: Int = -1) {
    popupWindow.update(x, y, width, height)
  }

  fun setOnDismissListener(listener: () -> Unit) {
    popupWindow.setOnDismissListener(listener)
  }

  var width: Int
    get() = popupWindow.width
    set(value) {
      popupWindow.width = value
    }

  var height: Int
    get() = popupWindow.height
    set(value) {
      popupWindow.height = value
    }

  var contentView: View
    get() = popupWindow.contentView
    set(value) {
      popupWindow.contentView = value
    }

  var isClippingEnabled: Boolean
    get() = popupWindow.isClippingEnabled
    set(value) {
      popupWindow.isClippingEnabled = value
    }
}