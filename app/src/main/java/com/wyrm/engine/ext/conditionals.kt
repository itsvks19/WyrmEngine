/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

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