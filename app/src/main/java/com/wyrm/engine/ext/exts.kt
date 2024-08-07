/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

@file:JvmName("Utils")

package com.wyrm.engine.ext

import androidx.annotation.ColorInt
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

fun String?.toFloat(): Float {
  if (this == null) return 0f

  try {
    if (this.isEmpty()) return 0f
    if (this.contains(",")) {
      return NumberFormat.getInstance(Locale.getDefault()).parse(this)?.toFloat() ?: 0f
    }
    if (this.contains(".")) {
      return NumberFormat.getInstance(Locale.US).parse(this)?.toFloat() ?: 0f
    }
    return NumberFormat.getInstance(Locale.ROOT).parse(this)?.toFloat() ?: 0f
  } catch (e: Exception) {
    e.printStackTrace()
    return 0f
  }
}

fun Float.toDecimals(i: Int): Float {
  val decimalFormat = DecimalFormat()
  decimalFormat.maximumFractionDigits = i
  return decimalFormat.format(this).toFloat()
}

fun @receiver:ColorInt Int.colorToHexString(): String {
  return String.format("#%08X", this)
}
