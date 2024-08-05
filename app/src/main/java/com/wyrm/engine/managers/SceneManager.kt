package com.wyrm.engine.managers

import com.wyrm.engine.graphics.scene.Scene

class SceneManager {
  companion object {
    @JvmStatic
    val instance by lazy { SceneManager() }
  }

  private var currentScene: Scene? = null

  fun loadScene(scene: Scene) {
    currentScene = scene
  }

  private val scenes = mutableListOf<Scene>()

  val mainScene get() = scenes.first()

  fun addScene(scene: Scene) {
    scenes.add(scene)
  }
}