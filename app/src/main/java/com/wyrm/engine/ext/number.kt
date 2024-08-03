package com.wyrm.engine.ext

import com.blankj.utilcode.util.SizeUtils
import glm_.glm

val Number.dp: Number
  get() = SizeUtils.dp2px(this.toFloat())

val Number.px: Number
  get() = SizeUtils.px2dp(this.toFloat())

val Number.radians: Number
  get() = glm.radians(this.toFloat())