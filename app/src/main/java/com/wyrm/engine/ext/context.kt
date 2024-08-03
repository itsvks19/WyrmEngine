package com.wyrm.engine.ext

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.annotation.AttrRes
import androidx.core.app.ActivityOptionsCompat
import com.google.android.material.color.MaterialColors
import com.wyrm.engine.core.Core
import java.io.IOException

@JvmOverloads
fun <T> Context.open(className: Class<T>, newTask: Boolean = false) {
  val intent = Intent(this, className)
  if (newTask)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
  startActivity(
    intent,
    getEmptyActivityBundle()
  )
}

fun Context.getEmptyActivityBundle(): Bundle? {
  return ActivityOptionsCompat.makeCustomAnimation(
    this,
    android.R.anim.fade_in,
    android.R.anim.fade_out
  ).toBundle()
}

fun Context.readFromAsset(path: String): String = try {
  assets.open(path).bufferedReader().use { it.readText() }
} catch (err: IOException) {
  err.printStackTrace()
  ""
}

fun Context.bitmapFromAsset(path: String) = try {
  BitmapFactory.decodeStream(assets.open(path))
} catch (err: IOException) {
  err.printStackTrace()
  null
}

fun Context.getThemeColor(@AttrRes attr: Int) = MaterialColors.getColor(this, attr, 0)

fun getCoreContext(): Context = Core.getInstance().context

fun getCoreInstance(): Core = Core.getInstance()