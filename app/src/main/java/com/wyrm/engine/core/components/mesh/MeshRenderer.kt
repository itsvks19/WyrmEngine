package com.wyrm.engine.core.components.mesh

import com.blankj.utilcode.util.ToastUtils
import com.wyrm.engine.core.Core
import com.wyrm.engine.core.components.Component
import com.wyrm.engine.core.components.camera.Camera
import com.wyrm.engine.core.configs.ScreenConfig
import com.wyrm.engine.ext.getCoreContext
import com.wyrm.engine.ext.runOnUiThread
import com.wyrm.engine.graphics.shapes.Plane
import glm_.func.rad
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3

class MeshRenderer : Component() {
  private val mesh = Mesh("engine/models/primitives/cylinder.obj", true)
  private lateinit var projection: Mat4
  val camera = Camera()
  private val plane = Plane()

  override fun onStart() {
    mesh.load()
    plane.setup(getCoreContext())
  }

  override fun onRepeat() {
    projection = glm.perspective(
      camera.zoom.rad,
      (ScreenConfig.instance.glWidth / ScreenConfig.instance.glHeight).toFloat(),
      0.1f,
      1000.0f
    )

    val model = Mat4.identity
    model.scaleAssign(Vec3(0.1f, 1f, 0.1f))
    mesh.draw(model, camera.viewMatrix, projection)

    plane.draw(projection, camera)

    if (Core.getInstance().inputManager.getTouch(0)?.isUp == true) {
      runOnUiThread {
        ToastUtils.showShort("0 up")
      }
    } else if (Core.getInstance().inputManager.getTouch(0)?.isDown == true) {
      runOnUiThread {
        ToastUtils.showShort("0 down")
      }
    }

    camera.onRepeat()
  }

  fun onChanged() {
    projection = glm.perspective(
      camera.zoom.rad,
      (ScreenConfig.instance.glWidth / ScreenConfig.instance.glHeight).toFloat(),
      0.1f,
      1000.0f
    )
  }
}