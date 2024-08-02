package com.wyrm.engine.ext

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory

fun Context.getBitmapFromAssets(assetName: String): Bitmap? {
  return try {
    assets.open(assetName).use { inputStream ->
      BitmapFactory.decodeStream(inputStream)
    }
  } catch (e: Exception) {
    e.printStackTrace()
    toast(e.message)
    null
  }
}
