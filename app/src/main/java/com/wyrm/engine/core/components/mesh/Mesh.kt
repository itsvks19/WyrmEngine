/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.components.mesh

import android.opengl.GLES30
import com.wyrm.engine.core.components.color.Color
import com.wyrm.engine.ext.FLOAT_SIZE
import com.wyrm.engine.ext.INT_SIZE
import com.wyrm.engine.ext.getCoreContext
import com.wyrm.engine.ext.readFromAsset
import com.wyrm.engine.graphics.shader.Config
import com.wyrm.engine.graphics.shader.Shader
import de.javagl.obj.ObjData
import de.javagl.obj.ObjReader
import de.javagl.obj.ObjUtils
import glm_.mat4x4.Mat4
import java.io.File
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.FloatBuffer
import java.nio.IntBuffer

class Mesh @JvmOverloads constructor(
  private val filePath: String,
  private val isFromAsset: Boolean = false
) {
  private lateinit var indices: IntBuffer
  private lateinit var vertices: FloatBuffer
  private lateinit var uvs: FloatBuffer
  private lateinit var normals: FloatBuffer

  private lateinit var shader: Shader

  private var vbo = IntArray(1)
  private var vao = IntArray(1)

  private val color = Color.WHITE

  internal fun load() {
    val obj = if (isFromAsset) {
      getCoreContext().assets.open(filePath).bufferedReader().use { ObjReader.read(it) }
    } else File(filePath).bufferedReader().use { ObjReader.read(it) }

    val renderableObj = ObjUtils.convertToRenderable(obj)

    indices = ObjData.getFaceVertexIndices(renderableObj)
    vertices = ObjData.getVertices(renderableObj)
    uvs = ObjData.getTexCoords(renderableObj, 2)
    normals = ObjData.getNormals(renderableObj)

    shader = Shader(
      "Simple",
      getCoreContext().readFromAsset("engine/shaders/simple.vert"),
      getCoreContext().readFromAsset("engine/shaders/simple.frag"),
      Config(getCoreContext().readFromAsset("engine/shaders/diffuse.config")),
    ).also { it.loadShader() }

    setupBuffers()
  }

  private fun setupBuffers() {
    val capacity = vertices.capacity() + normals.capacity() + uvs.capacity()
    val buffer = ByteBuffer.allocateDirect(capacity * FLOAT_SIZE).run {
      order(ByteOrder.nativeOrder())
      asFloatBuffer()
    }

    while (vertices.hasRemaining()) {
      buffer.put(vertices.get())
      buffer.put(vertices.get())
      buffer.put(vertices.get())
      buffer.put(normals.get())
      buffer.put(normals.get())
      buffer.put(normals.get())
      buffer.put(uvs.get())
      buffer.put(uvs.get())
    }

    buffer.position(0)

    GLES30.glGenVertexArrays(1, vao, 0)
    GLES30.glBindVertexArray(vao[0])

    GLES30.glGenBuffers(1, vbo, 0)
    GLES30.glBindBuffer(GLES30.GL_ARRAY_BUFFER, vbo[0])
    GLES30.glBufferData(
      GLES30.GL_ARRAY_BUFFER,
      buffer.capacity() * FLOAT_SIZE,
      buffer,
      GLES30.GL_STATIC_DRAW
    )

    GLES30.glEnableVertexAttribArray(0)
    GLES30.glVertexAttribPointer(
      0,
      3,
      GLES30.GL_FLOAT,
      false,
      8 * FLOAT_SIZE,
      0
    )
    GLES30.glEnableVertexAttribArray(1)
    GLES30.glVertexAttribPointer(
      1,
      3,
      GLES30.GL_FLOAT,
      false,
      8 * FLOAT_SIZE,
      3 * FLOAT_SIZE
    )
    GLES30.glEnableVertexAttribArray(2)
    GLES30.glVertexAttribPointer(
      2,
      2,
      GLES30.GL_FLOAT,
      false,
      8 * FLOAT_SIZE,
      6 * FLOAT_SIZE
    )

    val ibo = IntArray(1)
    GLES30.glGenBuffers(1, ibo, 0)
    GLES30.glBindBuffer(GLES30.GL_ELEMENT_ARRAY_BUFFER, ibo[0])
    GLES30.glBufferData(
      GLES30.GL_ELEMENT_ARRAY_BUFFER,
      indices.capacity() * INT_SIZE,
      indices,
      GLES30.GL_STATIC_DRAW
    )
  }

  fun draw(modelMatrix: Mat4, viewMatrix: Mat4, projectionMatrix: Mat4) {
    shader.use()
    shader.setMat4("uVMatrix", viewMatrix)
    shader.setMat4("uMMatrix", modelMatrix)
    shader.setMat4("uPMatrix", projectionMatrix)
    shader.setColor("color", color)

    GLES30.glBindVertexArray(vao[0])
    GLES30.glDrawElements(GLES30.GL_TRIANGLES, indices.capacity(), GLES30.GL_UNSIGNED_INT, 0)
  }
}