package com.wyrm.engine.ext

import android.graphics.BitmapFactory
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.widget.ImageView
import androidx.annotation.StringRes
import com.google.android.material.R.attr
import com.wyrm.engine.model.project.Icon

fun ImageView.setIcon(icon: Icon) {
  setImageBitmap(BitmapFactory.decodeFile(icon.path))
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.isGone() = visibility == View.GONE

fun View.isInvisible() = visibility == View.INVISIBLE

fun View.show() {
  visibility = View.VISIBLE
}

fun View.hide() {
  visibility = View.GONE
}

fun View.invisible() {
  visibility = View.INVISIBLE
}

@JvmOverloads
fun View.rotate(degrees: Float, resetToZero: Boolean = false, duration: Long = 400) {
  if (resetToZero) rotation = 0f
  animate().rotation(degrees).setDuration(duration).start()
}

fun View.getString(@StringRes resId: Int) = context.getString(resId)

fun View.applyDefaultBackgroundForPopupWindow() {
  GradientDrawable().apply {
    shape = GradientDrawable.RECTANGLE
    cornerRadius = 8f.dp.toFloat()
    setColor(context.getThemeColor(attr.colorSurface))
    setStroke(
      1,
      context.getThemeColor(attr.colorOnSurface)
    )
    background = this
  }
}