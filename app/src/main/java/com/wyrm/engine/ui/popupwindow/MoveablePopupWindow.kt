/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.ui.popupwindow

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

@SuppressLint("ClickableViewAccessibility")
abstract class MoveablePopupWindow<T : ViewBinding>(
  context: Context,
  bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> T
) : BasePopupWindow<T>(context, bindingInflater) {
  var currentX = 0
  var currentY = 0
  private var dx = 0
  private var dy = 0

  @JvmOverloads
  protected fun setupMoveView(view: View, updt: (Int, Int) -> Unit = { _, _ -> }) {
    view.setOnTouchListener { _, event ->
      when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          dx = (currentX - event.rawX).toInt()
          dy = (currentY - event.rawY).toInt()
          true
        }

        MotionEvent.ACTION_MOVE -> {
          currentX = (event.rawX + dx).toInt()
          currentY = (event.rawY + dy).toInt()
          update(currentX, currentY)
          updt(currentX, currentY)
          true
        }

        MotionEvent.ACTION_OUTSIDE -> {
          dismiss()
          true
        }

        else -> false
      }
    }
  }
}