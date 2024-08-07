/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

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