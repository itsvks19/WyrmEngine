package com.wyrm.engine.graphics.scene

import com.wyrm.engine.core.objects.GameObject

class Scene(
  val name: String,
) {
  val objects = mutableListOf<GameObject>()

  fun addGameObject(gameObject: GameObject) {
    objects.add(gameObject)
  }
}