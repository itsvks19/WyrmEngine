package com.wyrm.engine.core.components.color

import androidx.annotation.ColorInt

class Color(
  @JvmField
  var r: Float,
  @JvmField
  var g: Float,
  @JvmField
  var b: Float,
  @JvmField
  var a: Float
) {
  companion object {
    @JvmField
    val BLACK = Color(0f, 0f, 0f, 1f)

    @JvmField
    val WHITE = Color(1f, 1f, 1f, 1f)

    @JvmField
    val RED = Color(1f, 0f, 0f, 1f)

    @JvmField
    val GREEN = Color(0f, 1f, 0f, 1f)

    @JvmField
    val BLUE = Color(0f, 0f, 1f, 1f)

    @JvmField
    val YELLOW = Color(1f, 1f, 0f, 1f)

    @JvmField
    val CYAN = Color(0f, 1f, 1f, 1f)

    @JvmField
    val MAGENTA = Color(1f, 0f, 1f, 1f)

    @JvmField
    val SKY_BLUE = Color(0.53f, 0.81f, 0.92f, 1f) // Sky color

    // Space color, almost black with a hint of blue
    @JvmField
    val SPACE_BLACK = Color(0.02f, 0.02f, 0.08f, 1f)

    @JvmField
    val SUNSET_ORANGE = Color(1f, 0.64f, 0f, 1f) // Sunset color

    @JvmField
    val GRASS_GREEN = Color(0.13f, 0.55f, 0.13f, 1f) // Grass color

    @JvmField
    val SANDY_BROWN = Color(0.96f, 0.64f, 0.38f, 1f) // Sand color

    @JvmField
    val OCEAN_BLUE = Color(0f, 0.5f, 0.75f, 1f) // Ocean color

    @JvmField
    val LAVENDER = Color(0.9f, 0.9f, 0.98f, 1f) // Lavender color

    @JvmField
    val EARTH_BROWN = Color(0.54f, 0.27f, 0.07f, 1f) // Earth/soil color

    @JvmStatic
    fun fromColorInt(@ColorInt color: Int): Color {
      return Color(
        ((color shr 16) and 0xFF) / 255f,
        ((color shr 8) and 0xFF) / 255f,
        (color and 0xFF) / 255f,
        ((color shr 24) and 0xFF) / 255f
      )
    }

    @JvmStatic
    fun fromHexString(color: String): Color {
      val c = android.graphics.Color.parseColor(color)
      return fromColorInt(c)
    }
  }

  @ColorInt
  fun toColorInt(): Int {
    return ((a * 255).toInt() shl 24) or
        ((r * 255).toInt() shl 16) or
        ((g * 255).toInt() shl 8) or
        (b * 255).toInt()
  }
}
