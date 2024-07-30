package com.wyrm.engine.core.components.console

import java.util.LinkedList

class Console {
  companion object {
    private const val TAG = "CONSOLE LOG"
    private const val TAG_ERROR = "CONSOLE ERROR"
    private const val TAG_MSG = "CONSOLE MESSAGE"
  }

  val logs: MutableList<Log> = LinkedList()

  fun log(message: String) {
    android.util.Log.d(TAG, message)
    logs.add(Log("editor", message))
  }

  fun log(value: Boolean) {
    log(value.toString())
  }

  fun log(i: Int) = log("$i")

  fun log(list: List<String>) {
    list.forEachIndexed { index, s ->
      log("LIST $index: $s")
    }
  }

  fun logError(message: String) {
    android.util.Log.e(TAG_ERROR, message)
    logs.add(Log("error", message))
  }

  fun logError(value: Float) = logError("$value")

  fun logError(i: Int) = logError("$i")

  fun logMessage(message: String) {
    android.util.Log.d(TAG_MSG, message)
    logs.add(Log("message", message))
  }

  fun logMessage(value: Float) = logMessage("$value")

  fun logMessage(i: Int) = logMessage("$i")
}