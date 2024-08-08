/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import com.wyrm.engine.Constants
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.activities.project.ProjectsActivity
import com.wyrm.engine.core.Core
import com.wyrm.engine.core.cpp.renderer.ImGuiRenderer
import com.wyrm.engine.core.objects.GameObject
import com.wyrm.engine.databinding.ActivityEditorBinding
import com.wyrm.engine.ext.decrypt
import com.wyrm.engine.ext.encrypt
import com.wyrm.engine.ext.fromJson
import com.wyrm.engine.ext.open
import com.wyrm.engine.ext.toJson
import com.wyrm.engine.graphics.scene.Scene
import com.wyrm.engine.managers.ProjectManager
import com.wyrm.engine.managers.SceneManager
import com.wyrm.engine.model.project.Project
import java.io.File

@SuppressLint("SetTextI18n")
class EditorActivity : BaseActivity<ActivityEditorBinding>(ActivityEditorBinding::inflate) {

  private lateinit var project: Project

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    nativeInit(this)
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    project = requireNotNull(ProjectManager.instance.openedProject) { "No opened project" }

    Core.getInstance().onStartEngine(this, this)

    run {
      val scene = Scene("Test").also {
        it.addGameObject(GameObject("Cube"))
        it.addGameObject(GameObject("Sphere"))
        it.addGameObject(GameObject("Plane"))
      }

      val json = scene.toJson()
      File(Constants.FILES_PATH, "scene.wscene").writeText(encrypt(json))

      SceneManager.instance.addScene(scene)
    }

    val json = decrypt(File(Constants.FILES_PATH, "scene.wscene").readText())
    val scene = json.fromJson<Scene>()
  }

  fun updateUiOnRepeat() {

  }

  private fun closeEditor() {
    runOnUiThread {
      open(ProjectsActivity::class.java)
      finish()
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    ProjectManager.instance.closeProject()
    ImGuiRenderer.destroy()
    Core.getInstance().destroy()
  }

  private external fun nativeInit(activity: EditorActivity)
}