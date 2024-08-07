/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

@file:JvmName("BufferUtils")

package com.wyrm.engine.utils

import com.wyrm.engine.ext.FLOAT_SIZE
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer

fun FloatArray.toFloatBuffer(): FloatBuffer =
  ByteBuffer.allocateDirect(this.size * FLOAT_SIZE).run {
    order(ByteOrder.nativeOrder())

    asFloatBuffer().apply {
      put(this@toFloatBuffer)
      position(0)
    }
  }

fun FloatBuffer.toFloatArray() = FloatArray(remaining(), ::get)