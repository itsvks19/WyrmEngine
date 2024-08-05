package com.wyrm.engine.activities

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import com.wyrm.engine.Constants
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.adapters.EditorTopBarMenuAdapter
import com.wyrm.engine.adapters.graphics.ObjectListAdapter
import com.wyrm.engine.core.Core
import com.wyrm.engine.core.components.camera.Camera
import com.wyrm.engine.core.memory.Profiler
import com.wyrm.engine.core.objects.GameObject
import com.wyrm.engine.databinding.ActivityEditorBinding
import com.wyrm.engine.ext.decrypt
import com.wyrm.engine.ext.encrypt
import com.wyrm.engine.ext.fromJson
import com.wyrm.engine.ext.open
import com.wyrm.engine.ext.toDecimals
import com.wyrm.engine.ext.toJson
import com.wyrm.engine.ext.toast
import com.wyrm.engine.graphics.scene.Scene
import com.wyrm.engine.managers.ProjectManager
import com.wyrm.engine.managers.SceneManager
import com.wyrm.engine.model.MenuItem
import com.wyrm.engine.model.project.Project
import com.wyrm.engine.ui.colorpicker.ColorPicker
import com.wyrm.engine.ui.popupmenu.WyrmPopupMenu
import java.io.File
import kotlin.reflect.KMutableProperty0

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

    val json = decrypt(File(Constants.FILES_PATH, "scene.wscene").readText())
    val scene = json.fromJson<Scene>()

    binding.topBar.apply {
      val menu = mutableListOf(
        MenuItem("File") { toast("File") },
        MenuItem("Edit") { toast("Edit") },
        MenuItem("View") { toast("View") },
        MenuItem("Scene") {
          WyrmPopupMenu(it).apply {
            addMenuItem("Light Settings", WyrmPopupMenu(it).apply {
              addMenuItem("Space color") {
                ColorPicker(
                  this@EditorActivity,
                  SceneManager.instance.mainScene.lightSettings.spaceColor
                ).apply {
                  onColorPicked = {
                    SceneManager.instance.mainScene.lightSettings.spaceColor = it
                  }
                  show()
                }
              }
              addMenuItem("Ambient color") { toast("color") }
            })
            show()
          }
        },
        MenuItem("Code") { open(CodeActivity::class.java) },
        MenuItem("Build") { toast("Build") },
        MenuItem("Help") { toast("Help") },
      )

      dropdownMenu.apply {
        layoutManager = LinearLayoutManager(
          this@EditorActivity,
          LinearLayoutManager.HORIZONTAL,
          false
        )
        adapter = EditorTopBarMenuAdapter(menu)
      }

      info.apply {
        text = "v${Constants.APP_VERSION}"
        setOnClickListener { }
      }
    }

    binding.objectList.apply {
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

  private fun updateInfoText(text: String) {
    binding.topBar.info.text = text
  }

  fun updateUiOnRepeat() {
    updateInfoText("FPS: ${Profiler.frameRate}")
  }

  fun updateFov(fov: Float) {
    binding.fov.text = "FOV: ${fov.toDecimals(1)}"
  }

  fun handleButtonTouch(camera: Camera) {
    binding.apply {
      setupButton(forward, camera::isMovingForward)
      setupButton(backward, camera::isMovingBackward)
      setupButton(left, camera::isMovingLeft)
      setupButton(right, camera::isMovingRight)
      setupButton(up, camera::isMovingUp)
      setupButton(down, camera::isMovingDown)
    }
  }

  @SuppressLint("ClickableViewAccessibility")
  private fun setupButton(button: ImageView, cameraMovement: KMutableProperty0<Boolean>) {
    button.setOnTouchListener { _, event ->
      when (event.actionMasked) {
        MotionEvent.ACTION_DOWN -> {
          button.imageTintList = ColorStateList.valueOf(Color.GREEN)
          cameraMovement.set(true)
          true
        }

        MotionEvent.ACTION_UP -> {
          button.imageTintList = ColorStateList.valueOf(Color.WHITE)
          cameraMovement.set(false)
          true
        }

        else -> false
      }
    }
  }

  override fun onDestroy() {
    super.onDestroy()
    ProjectManager.instance.closeProject()
    Core.getInstance().destroy()
  }
}