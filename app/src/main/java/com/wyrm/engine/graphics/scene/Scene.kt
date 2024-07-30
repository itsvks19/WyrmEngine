package com.wyrm.engine.graphics.scene

import com.wyrm.engine.core.objects.GameObject
import java.io.Serial
import java.io.Serializable

class Scene(
  val name: String,
) : Serializable {
  companion object {
    @Serial
    private const val serialVersionUID = 1L
  }

  val objects = mutableListOf<GameObject>()

  fun addGameObject(gameObject: GameObject) {
    objects.add(gameObject)
  }
}