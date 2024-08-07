/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.ext

import com.blankj.utilcode.util.SizeUtils
import glm_.glm

val Number.dp: Number
  get() = SizeUtils.dp2px(this.toFloat())

val Number.px: Number
  get() = SizeUtils.px2dp(this.toFloat())

val Number.radians: Number
  get() = glm.radians(this.toFloat())