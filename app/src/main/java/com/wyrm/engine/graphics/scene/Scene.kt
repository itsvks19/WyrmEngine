package com.wyrm.engine.graphics.scene

import android.content.Context
import com.wyrm.engine.core.engine.Engine
import com.wyrm.engine.core.objects.GameObject
import com.wyrm.engine.graphics.scene.settings.LightSettings
import java.io.Serial
import java.io.Serializable

class Scene @JvmOverloads constructor(
  val name: String,
  var lightSettings: LightSettings = LightSettings()
) : Serializable {
  companion object {
    @Serial
    private const val serialVersionUID = 1L
  }

  val objects = mutableListOf<GameObject>()

  internal fun onStart(context: Context, engine: Engine) {
    objects.forEach { it.onStart(context, engine) }
  }

  internal fun onRepeat(context: Context, engine: Engine) {
    objects.forEach { it.onRepeat(context, engine) }
  }

  fun addGameObject(gameObject: GameObject) {
    objects.add(gameObject)
  }
}