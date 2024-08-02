package com.wyrm.engine.ext

import android.content.res.Resources
import glm_.glm

val Number.dp: Number
  get() = (this.toFloat() / Resources.getSystem().displayMetrics.density).toInt()

val Number.px: Number
  get() = (this.toFloat() * Resources.getSystem().displayMetrics.density).toInt()

val Number.radians: Number
  get() = glm.radians(this.toFloat())