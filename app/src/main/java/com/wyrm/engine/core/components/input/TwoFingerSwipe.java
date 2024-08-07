/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.components.input;

import glm_.vec2.Vec2;

public class TwoFingerSwipe {
  private Vec2 factor;
  private Vec2 slide;

  public TwoFingerSwipe(Vec2 initialVector, Vec2 comparisonVector) {
    if (factor == null) factor = new Vec2();

    if (initialVector.getX() == 0f) factor.setX(0f);
    else if (initialVector.getX() > 0f) {
      if (comparisonVector.getX() == 0f) factor.setX(0f);
      else if (comparisonVector.getX() > 0f) {
        factor.setX(Math.max(initialVector.getX(), comparisonVector.getX()));
      } else factor.setX(0f);
    } else if (comparisonVector.getX() < 0f) {
      factor.setX(Math.min(initialVector.getX(), comparisonVector.getX()));
    } else factor.setX(0f);

    if (initialVector.getY() == 0f) factor.setY(0f);
    else if (initialVector.getY() > 0f) {
      if (comparisonVector.getY() == 0f) factor.setY(0f);
      else if (comparisonVector.getY() > 0f) {
        factor.setY(Math.max(initialVector.getY(), comparisonVector.getY()));
      } else factor.setY(0f);
    } else if (comparisonVector.getY() < 0f) {
      factor.setY(Math.min(initialVector.getY(), comparisonVector.getY()));
    } else factor.setY(0f);
  }

  public Vec2 getSlide() {
    if (slide == null) slide = new Vec2();
    return slide;
  }

  public Vec2 getFactor() {
    if (factor == null) factor = new Vec2();
    return factor;
  }
}
