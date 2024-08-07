/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

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
