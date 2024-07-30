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