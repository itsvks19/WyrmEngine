/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.ext

import android.os.Handler
import android.os.Looper

fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun runAsync(action: () -> Unit) = Thread(Runnable(action)).start()

fun runOnUiThread(action: () -> Unit) {
  when {
    isMainThread() -> action.invoke()
    else -> Handler(Looper.getMainLooper()).post(Runnable(action))
  }
}