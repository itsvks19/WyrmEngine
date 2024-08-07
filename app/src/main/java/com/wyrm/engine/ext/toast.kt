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
import android.widget.Toast

fun Context.toast(message: String?) {
  toast(message, Toast.LENGTH_SHORT)
}

fun Context.toast(message: Int) {
  toast(getString(message))
}

private fun Context.toast(message: String?, duration: Int) {
  Toast.makeText(this, message, duration).show()
}

fun Context.toastLong(message: String?) {
  toast(message, Toast.LENGTH_LONG)
}

fun Context.toastLong(message: Int) {
  toastLong(getString(message))
}

object WyrmToast {
  @JvmStatic
  fun show(message: String) {
    runOnUiThread { getCoreContext().toast(message) }
  }

  @JvmStatic
  fun show(message: Int) {
    runOnUiThread { getCoreContext().toast(message) }
  }

  @JvmStatic
  fun showLong(message: String) {
    runOnUiThread { getCoreContext().toastLong(message) }
  }

  @JvmStatic
  fun showLong(message: Int) {
    runOnUiThread { getCoreContext().toastLong(message) }
  }
}