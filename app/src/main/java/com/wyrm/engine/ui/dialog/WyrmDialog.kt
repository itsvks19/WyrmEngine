/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.ui.dialog

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.text.method.LinkMovementMethod
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AlertDialog
import com.google.android.material.R.attr
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.wyrm.engine.databinding.LayoutWyrmDialogBinding
import com.wyrm.engine.ext.dp
import com.wyrm.engine.ext.getThemeColor
import com.wyrm.engine.ext.hide
import com.wyrm.engine.ext.show

class WyrmDialog(
  private val context: Context
) {
  private var binding: LayoutWyrmDialogBinding
  private var builder: MaterialAlertDialogBuilder = MaterialAlertDialogBuilder(context)
  private var dialog: AlertDialog

  init {
    binding = LayoutWyrmDialogBinding.inflate(builder.create().layoutInflater)
    builder.background = GradientDrawable().apply {
      shape = GradientDrawable.RECTANGLE
      cornerRadius = 8f.dp.toFloat()
      setColor(context.getThemeColor(attr.colorSurface))
      setStroke(2, context.getThemeColor(attr.colorOutline))
    }

    binding.apply {
      title.hide()
      positiveButton.hide()
      negativeButton.hide()
      message.movementMethod = LinkMovementMethod.getInstance()

      builder.setView(root)
    }

    dialog = builder.create()
    setLayout(
      350.dp.toInt(),
      WindowManager.LayoutParams.WRAP_CONTENT
    )
  }

  fun setLayout(width: Int, height: Int) {
    dialog.window?.setLayout(width, height)
  }

  fun setView(view: View?): WyrmDialog {
    binding.container.show()
    binding.container.addView(view)
    return this
  }

  fun setCancelable(cancelable: Boolean): WyrmDialog {
    dialog.setCancelable(cancelable)
    return this
  }

  fun setMessage(resId: Int): WyrmDialog {
    setMessage(context.getString(resId))
    return this
  }

  fun setMessage(msg: CharSequence?): WyrmDialog {
    binding.apply {
      message.show()
      message.text = msg
    }
    return this
  }

  fun setNegativeButton(buttonText: CharSequence?, listener: OnClickListener): WyrmDialog {
    binding.apply {
      negativeButton.show()
      negativeButton.text = buttonText
      negativeButton.setOnClickListener { v -> listener.onClick(this@WyrmDialog, v) }
    }
    return this
  }

  fun setNegativeButton(resId: Int, listener: OnClickListener): WyrmDialog {
    setNegativeButton(context.getString(resId), listener)
    return this
  }

  fun setPositiveButton(resId: Int, listener: OnClickListener): WyrmDialog {
    setPositiveButton(context.getString(resId), listener)
    return this
  }

  fun setPositiveButton(buttonText: CharSequence?, listener: OnClickListener): WyrmDialog {
    binding.apply {
      positiveButton.show()
      positiveButton.text = buttonText
      positiveButton.setOnClickListener { v -> listener.onClick(this@WyrmDialog, v) }
    }
    return this
  }

  fun setTitle(resId: Int): WyrmDialog {
    setTitle(context.getString(resId))
    return this
  }

  fun setTitle(text: CharSequence?): WyrmDialog {
    binding.apply {
      title.show()
      title.text = text
    }
    return this
  }

  fun show() {
    dialog.show()
  }

  fun hide() {
    dialog.hide()
  }

  fun cancel() {
    dialog.cancel()
  }

  fun dismiss() {
    dialog.dismiss()
  }

  fun setButtonEnabled(buttonType: ButtonType, enabled: Boolean) {
    when (buttonType) {
      ButtonType.POSITIVE -> binding.positiveButton.isEnabled = enabled
      ButtonType.NEGATIVE -> binding.negativeButton.isEnabled = enabled
      ButtonType.NEUTRAL -> {}
    }
  }

  enum class ButtonType {
    POSITIVE,
    NEGATIVE,
    NEUTRAL
  }

  fun interface OnClickListener {
    fun onClick(dialog: WyrmDialog, view: View)
  }
}