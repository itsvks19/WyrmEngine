package com.wyrm.engine.graphics;

import static glm_.Java.glm;

import glm_.mat4x4.Mat4;
import glm_.vec3.Vec3;

public class Camera {
  private Mat4 viewMatrix;

  private Vec3 position = new Vec3(0f, 0f, 3f);
  private Vec3 lookAt = new Vec3(0f, 0f, 0f);
  private Vec3 up = new Vec3(0f, 1f, 0f);

  private Mat4 rotation = Mat4.Companion.getIdentity();

  private Vec3 translation = new Vec3();

  public Camera() {
    viewMatrix = glm.lookAt(position, lookAt, up);
  }

  public void updateViewMatrix() {
    viewMatrix = glm.lookAt(position, lookAt, up);
    viewMatrix.timesAssign(rotation);
  }

  public Mat4 getViewMatrix() {
    return viewMatrix;
  }

  public void translate(float x, float y, float z) {
    translation.setX(x);
    translation.setY(y);
    translation.setZ(z);
    rotation.translateAssign(translation);
  }

  public void rotate(float angle) {
    rotation.rotateAssign(angle, 0f, 1f, 0f);
  }

  public Vec3 getPosition() {
    return position;
  }

  public void setPosition(Vec3 position) {
    this.position = position;
  }

  public Vec3 getTranslation() {
    return translation;
  }
}
