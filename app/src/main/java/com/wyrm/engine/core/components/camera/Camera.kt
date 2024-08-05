package com.wyrm.engine.core.components.camera

import com.wyrm.engine.Time
import com.wyrm.engine.core.components.Component
import glm_.func.cos
import glm_.func.rad
import glm_.func.sin
import glm_.glm
import glm_.mat4x4.Mat4
import glm_.vec3.Vec3

class Camera @JvmOverloads constructor(
  val position: Vec3 = Vec3(0f, 0f, 3f),
  var up: Vec3 = Vec3(0f, 1f, 0f),
  var yaw: Float = YAW,
  var pitch: Float = PITCH
) : Component() {

  companion object {
    const val YAW = -90f
    const val PITCH = 0f
    const val SPEED = 3f
    const val SENSITIVITY = 100f
    const val ZOOM = 45f

    const val MIN_FOV = 10f
    const val MAX_FOV = 90f
  }

  var front = Vec3(0f, 0f, -1f)
  private var right = Vec3()
  private var worldUp = up

  var movementSpeed = SPEED
  var mouseSensitivity = SENSITIVITY
  var zoom = ZOOM

  var isMovingForward = false
  var isMovingBackward = false
  var isMovingLeft = false
  var isMovingRight = false
  var isMovingUp = false
  var isMovingDown = false

  override fun getTitle(): String {
    return "Camera"
  }

  override fun onStart() {
  }

  override fun onRepeat() {
    moveCamera(Time.getDeltaTime())
  }

  private fun moveCamera(deltaTime: Float) {
    if (isMovingForward) {
      processButtons(CameraMovement.FORWARD, deltaTime)
    }
    if (isMovingBackward) {
      processButtons(CameraMovement.BACKWARD, deltaTime)
    }
    if (isMovingLeft) {
      processButtons(CameraMovement.LEFT, deltaTime)
    }
    if (isMovingRight) {
      processButtons(CameraMovement.RIGHT, deltaTime)
    }
    if (isMovingUp) {
      processButtons(CameraMovement.UP, deltaTime)
    }
    if (isMovingDown) {
      processButtons(CameraMovement.DOWN, deltaTime)
    }
  }

  private fun processButtons(direction: CameraMovement, deltaTime: Float) {
    val velocity = movementSpeed * deltaTime

    when (direction) {
      CameraMovement.FORWARD -> position += front * velocity
      CameraMovement.BACKWARD -> position -= front * velocity
      CameraMovement.LEFT -> position -= right * velocity
      CameraMovement.RIGHT -> position += right * velocity
      CameraMovement.UP -> position += worldUp * velocity
      CameraMovement.DOWN -> position -= worldUp * velocity
    }
  }

  @Suppress("NAME_SHADOWING")
  @JvmOverloads
  fun processTouchMovement(xoffset: Float, yoffset: Float, constrainPitch: Boolean = true) {
    var xoffset = xoffset
    var yoffset = yoffset

    xoffset *= mouseSensitivity
    yoffset *= mouseSensitivity

    yaw += xoffset
    pitch += yoffset

    if (constrainPitch) {
      if (pitch > 60f) {
        pitch = 60f
      }
      if (pitch < -60f) {
        pitch = -60f
      }
    }
    updateCameraVectors()
  }

  fun processZoom(scaleFactor: Float) {
    zoom /= scaleFactor
    if (zoom < MIN_FOV) {
      zoom = MIN_FOV
    } else if (zoom > MAX_FOV) {
      zoom = MAX_FOV
    }
  }

  val viewMatrix: Mat4
    get() {
      return glm.lookAt(position, position + front, up)
    }

  private fun updateCameraVectors() {
    val front = Vec3()
    front.x = yaw.rad.cos * pitch.rad.cos
    front.y = pitch.rad.sin
    front.z = yaw.rad.sin * pitch.rad.cos
    this.front = glm.normalize(front)

    right = glm.normalize(glm.cross(front, worldUp))
    up = glm.normalize(glm.cross(right, front))
  }
}