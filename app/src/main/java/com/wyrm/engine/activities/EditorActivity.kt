package com.wyrm.engine.activities

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wyrm.engine.Constants
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.adapters.EditorTopBarMenuAdapter
import com.wyrm.engine.adapters.graphics.ObjectListAdapter
import com.wyrm.engine.core.Core
import com.wyrm.engine.core.components.camera.Camera
import com.wyrm.engine.core.objects.GameObject
import com.wyrm.engine.databinding.ActivityEditorBinding
import com.wyrm.engine.ext.decrypt
import com.wyrm.engine.ext.encrypt
import com.wyrm.engine.ext.fromJson
import com.wyrm.engine.ext.toDecimals
import com.wyrm.engine.ext.toJson
import com.wyrm.engine.graphics.scene.Scene
import com.wyrm.engine.managers.ProjectManager
import com.wyrm.engine.managers.SceneManager
import com.wyrm.engine.model.MenuItem
import com.wyrm.engine.model.project.Project
import java.io.File

@SuppressLint("SetTextI18n")
class EditorActivity : BaseActivity<ActivityEditorBinding>(ActivityEditorBinding::inflate) {

  private lateinit var project: Project

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
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

    binding.topBar.apply {
      val menu = mutableListOf(
        MenuItem("File"),
        MenuItem("Edit"),
        MenuItem("View"),
        MenuItem("Code"),
        MenuItem("Build"),
        MenuItem("Help"),
      )




      dropdownMenu.apply {
        layoutManager = LinearLayoutManager(
          this@EditorActivity,
          LinearLayoutManager.HORIZONTAL,
          false
        )
        adapter = EditorTopBarMenuAdapter(menu,this@EditorActivity)
      }

      info.apply {
        text = "v${Constants.APP_VERSION}"
        setOnClickListener { }
      }
    }

    binding.objectList.apply {
      val json = decrypt(File(Constants.FILES_PATH, "scene.wscene").readText())
      val scene = json.fromJson<Scene>()

      objectsList.apply {
        layoutManager = LinearLayoutManager(
          this@EditorActivity,
          LinearLayoutManager.VERTICAL,
          false
        )
        adapter = ObjectListAdapter(scene.objects)
      }
    }
  }

  fun updateInfoText(text: String) {
    binding.topBar.info.text = text
  }

  fun updateUiOnRepeat() {

  }

  fun updateFov(fov: Float) {
    binding.fov.text = "FOV: ${fov.toDecimals(1)}"
  }

  @SuppressLint("ClickableViewAccessibility")
  fun handleButtonTouch(camera: Camera) {
    binding.apply {
      forward.setOnTouchListener { _, event ->
        when (event.actionMasked) {
          MotionEvent.ACTION_DOWN -> {
            forward.imageTintList = ColorStateList.valueOf(Color.GREEN)
            camera.isMovingForward = true
            true
          }

          MotionEvent.ACTION_UP -> {
            forward.imageTintList = ColorStateList.valueOf(Color.WHITE)
            camera.isMovingForward = false
            true
          }

          else -> false
        }
      }
      backward.setOnTouchListener { _, event ->
        when (event.actionMasked) {
          MotionEvent.ACTION_DOWN -> {
            backward.imageTintList = ColorStateList.valueOf(Color.GREEN)
            camera.isMovingBackward = true
            true
          }

          MotionEvent.ACTION_UP -> {
            backward.imageTintList = ColorStateList.valueOf(Color.WHITE)
            camera.isMovingBackward = false
            true
          }

          else -> false
        }
      }
      left.setOnTouchListener { _, event ->
        when (event.actionMasked) {
          MotionEvent.ACTION_DOWN -> {
            left.imageTintList = ColorStateList.valueOf(Color.GREEN)
            camera.isMovingLeft = true
            true
          }

          MotionEvent.ACTION_UP -> {
            left.imageTintList = ColorStateList.valueOf(Color.WHITE)
            camera.isMovingLeft = false
            true
          }

          else -> false
        }
      }
      right.setOnTouchListener { _, event ->
        when (event.actionMasked) {
          MotionEvent.ACTION_DOWN -> {
            right.imageTintList = ColorStateList.valueOf(Color.GREEN)
            camera.isMovingRight = true
            true
          }

          MotionEvent.ACTION_UP -> {
            right.imageTintList = ColorStateList.valueOf(Color.WHITE)
            camera.isMovingRight = false
            true
          }

          else -> false
        }
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    ProjectManager.instance.closeProject()
    Core.getInstance().destroy()
  }
}