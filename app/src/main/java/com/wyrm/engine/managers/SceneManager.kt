package com.wyrm.engine.managers

import com.wyrm.engine.graphics.scene.Scene

class SceneManager {
  companion object {
    @JvmStatic
    val instance by lazy { SceneManager() }
  }

  private val scenes = mutableListOf<Scene>()

  fun getMainScene(): Scene {
    return scenes.first()
  }

  fun addScene(scene: Scene) {
    scenes.add(scene)
  }
}