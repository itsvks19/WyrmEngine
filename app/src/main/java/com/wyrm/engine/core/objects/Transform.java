/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.objects;

import glm_.quat.Quat;
import glm_.vec3.Vec3;

public class Transform {
  public Vec3 position = new Vec3();
  public Quat rotation = new Quat();
  public Vec3 scale = new Vec3(1f, 1f, 1f);

  public Vec3 getPosition() {
    return position;
  }

  public void setPosition(Vec3 position) {
    this.position = position;
  }

  public Quat getRotation() {
    return rotation;
  }

  public void setRotation(Quat rotation) {
    this.rotation = rotation;
  }

  public Vec3 getScale() {
    return scale;
  }

  public void setScale(Vec3 scale) {
    this.scale = scale;
  }
}
