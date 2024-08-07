/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.components.input.gesture;

import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class RotationGestureDetector {
  private static final int INVALID_POINTER_ID = MotionEvent.INVALID_POINTER_ID;

  private float fX, fY;
  private float mAngle;

  private int ptrId_1 = -1, ptrId_2 = -1;
  private float sX, sY;

  private OnRotationGestureListener listener;

  public RotationGestureDetector(OnRotationGestureListener listener) {
    this.listener = listener;
  }

  public boolean onTouchEvent(@NonNull MotionEvent event) {
    switch (event.getActionMasked()) {
      case MotionEvent.ACTION_DOWN -> ptrId_1 = event.getPointerId(event.getActionIndex());
      case MotionEvent.ACTION_UP -> ptrId_1 = -1;
      case MotionEvent.ACTION_MOVE -> {
        if (!(ptrId_1 == -1 || ptrId_2 == -1)) {
          try {
            float x = event.getX(event.findPointerIndex(ptrId_1));
            float y = event.getY(event.findPointerIndex(ptrId_1));
            mAngle = angleBetweenLines(
              fX, fY,
              sX, sY,
              event.getX(event.findPointerIndex(ptrId_2)),
              event.getY(event.findPointerIndex(ptrId_2)),
              x, y
            );
          } catch (Exception e) {
            e.printStackTrace();
          }

          if (listener != null) listener.onRotate(this);
        }
      }
      case MotionEvent.ACTION_CANCEL -> {
        ptrId_1 = -1;
        ptrId_2 = -1;
      }
      case MotionEvent.ACTION_POINTER_DOWN -> {
        try {
          ptrId_2 = event.getPointerId(event.getActionIndex());

          sX = event.getX(event.findPointerIndex(ptrId_1));
          sX = event.getY(event.findPointerIndex(ptrId_1));
          fX = event.getX(event.findPointerIndex(ptrId_2));
          fY = event.getY(event.findPointerIndex(ptrId_2));
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
      case MotionEvent.ACTION_POINTER_UP -> ptrId_2 = -1;
    }
    return true;
  }

  private float angleBetweenLines(
    float x1Start,
    float y1Start,
    float x1End,
    float y1End,
    float x2Start,
    float y2Start,
    float x2End,
    float y2End
  ) {
    float angle1 = (float) Math.atan2(y1Start - y1End, x1Start - x1End);
    float angle2 = (float) Math.atan2(y2Start - y2End, x2Start - x2End);

    float degrees = (float) (Math.toDegrees(angle1 - angle2) % 360f);

    if (degrees < -180f) degrees += 360f;

    return degrees > 180f ? degrees - 360f : degrees;
  }

  public float getAngle() {
    return mAngle;
  }
}
