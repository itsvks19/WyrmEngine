package com.wyrm.engine.graphics

import android.content.Context
import android.graphics.BitmapFactory
import android.opengl.GLES30
import android.opengl.GLUtils
import java.io.File

class Texture @JvmOverloads constructor(
  private val context: Context,
  private val path: String,
  private val isFromAssets: Boolean = false
) {
  var id: Int = 0
    get() {
      check(field != 0) { "Call load() before using texture" }
      return field
    }

  fun load() {
    val tmpId = IntArray(1)
    GLES30.glGenTextures(1, tmpId, 0)
    id = tmpId[0]

    GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, id)

    GLES30.glTexParameteri(
      GLES30.GL_TEXTURE_2D,
      GLES30.GL_TEXTURE_MIN_FILTER,
      GLES30.GL_LINEAR_MIPMAP_LINEAR
    )
    GLES30.glTexParameteri(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR)

    val bitmap = BitmapFactory.decodeStream(
      if (isFromAssets) context.assets.open(path) else File(path).inputStream()
    )
    GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, GLUtils.getInternalFormat(bitmap), bitmap, 0)
    GLES30.glGenerateMipmap(GLES30.GL_TEXTURE_2D)
  }
}
