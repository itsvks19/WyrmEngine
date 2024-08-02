package com.wyrm.engine.graphics;

import static glm_.Java.glm;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

public class Camera {
  private Mat4 viewMatrix;

  private Vec3 position = new Vec3(0f, 1f, 3f);
  private Vec3 lookAt = new Vec3(0f, 0f, 0f);
  private Vec3 up = new Vec3(0f, 1f, 0f);

  private Vec3 rotation = new Vec3();

  public Camera() {
    viewMatrix = glm.lookAt(position, lookAt, up);
  }

  public void updateViewMatrix() {
    viewMatrix = glm.lookAt(position, lookAt, up);
  }

  public Mat4 getViewMatrix() {
    viewMatrix = Mat4.Companion.getIdentity();
    viewMatrix = viewMatrix.rotateX(glm.radians(rotation.getX()));
    viewMatrix = viewMatrix.rotateY(glm.radians(rotation.getY()));
    viewMatrix = viewMatrix.translate(position.negate());
    return viewMatrix;
  }

  public Vec3 getPosition() {
    return position;
  }

  public void setPosition(Vec3 position) {
    this.position = position;
  }

  public void rotate(float dx, float dy, float dz) {
    rotation.setX(rotation.getX() + dx);
    rotation.setY(rotation.getY() + dy);
    rotation.setZ(rotation.getZ() + dz);
    float minVerticalAngle = -85.0f;
    float maxVerticalAngle = 85.0f;

    // Clamp the vertical angle
    if (this.rotation.getX() < minVerticalAngle) {
      this.rotation.setX(minVerticalAngle);
    } else if (this.rotation.getX() > maxVerticalAngle) {
      this.rotation.setX(maxVerticalAngle);
    }
  }
}
