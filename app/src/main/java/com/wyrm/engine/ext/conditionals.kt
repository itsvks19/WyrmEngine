package com.wyrm.engine.ext

inline fun doIf(condition: Boolean?, action: () -> Unit) {
  if (condition == true) action()
}

inline fun doIf(condition: () -> Boolean?, action: () -> Unit) {
  if (condition() == true) action()
}

inline fun doIf(any: Any?, action: () -> Unit) {
  if (any != null) action()
}