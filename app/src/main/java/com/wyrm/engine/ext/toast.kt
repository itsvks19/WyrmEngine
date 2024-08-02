package com.wyrm.engine.ext

import android.content.Context
import android.widget.Toast

fun Context.toast(message: String) {
  toast(message, Toast.LENGTH_SHORT)
}

fun Context.toast(message: Int) {
  toast(getString(message))
}

private fun Context.toast(message: String, duration: Int) {
  Toast.makeText(this, message, duration).show()
}

fun Context.toastLong(message: String) {
  toast(message, Toast.LENGTH_LONG)
}

fun Context.toastLong(message: Int) {
  toastLong(getString(message))
}