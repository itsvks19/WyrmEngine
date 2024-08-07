/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.graphics.shapes

import android.opengl.GLES30
import com.wyrm.engine.core.components.color.Color
import com.wyrm.engine.graphics.shader.Shader
import java.nio.ByteBuffer
import java.nio.ByteOrder

const val COORDS_PER_VERTEX = 3

@JvmField
var triangleCoords = floatArrayOf(
  0f, 0.62200844f, 0f,
  -0.5f, -0.31100425f, 0f,
  0.5f, -0.31100425f, 0f
)

class Triangle {
  private val vertexCount = triangleCoords.size / COORDS_PER_VERTEX
  private val vertexStride = COORDS_PER_VERTEX * 4

  private val color = Color.OCEAN_BLUE

  private var vertexBuffer = ByteBuffer.allocateDirect(triangleCoords.size * 4).run {
    order(ByteOrder.nativeOrder())

    asFloatBuffer().apply {
      put(triangleCoords)
      position(0)
    }
  }

  fun draw(shader: Shader) {
    shader.use()
    GLES30.glVertexAttribPointer(
      0,
      3,
      GLES30.GL_FLOAT,
      false,
      vertexStride,
      vertexBuffer
    )
    GLES30.glEnableVertexAttribArray(0)

    shader.setVec4("color", color.r, color.g, color.b, color.a)
    GLES30.glDrawArrays(GLES30.GL_TRIANGLES, 0, vertexCount)

    GLES30.glDisableVertexAttribArray(0)
  }

  companion object {
    @JvmField
    var vertices: FloatArray = floatArrayOf(
      -0.5f, -0.5f, 0.0f,
      0.5f, -0.5f, 0.0f,
      0.0f, 0.5f, 0.0f
    )
  }
}