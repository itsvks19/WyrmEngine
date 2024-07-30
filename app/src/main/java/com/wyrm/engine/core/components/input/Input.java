package com.wyrm.engine.core.components.input;

import android.content.Context;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;

import androidx.annotation.NonNull;

import com.wyrm.engine.Time;
import com.wyrm.engine.core.components.input.gesture.RotationGestureDetector;
import com.wyrm.engine.core.renderer.WyrmSurface;

import java.util.LinkedList;
import java.util.List;

import glm_.vec2.Vec2;

public class Input {
  private List<Touch> frontTouches = new LinkedList<>();
  private List<Key> keys = new LinkedList<>();
  private List<Touch> touches = new LinkedList<>();

  private boolean initialized = false;
  private float mPreviousX;
  private float mPreviousY;

  private float longClickMillis = 3000f;
  private float slidingDeadZone = 1f;

  private ScaleGestureDetector scaleGestureDetector;
  private RotationGestureDetector rotationGestureDetector;

  public Pinch pinch = null;
  public PinchRotate pinchRotate = null;
  public TwoFingerSwipe twoFingerSwipe = null;
  private TwoFingerSwipe twoFingerSwipeFront = null;
  private boolean lastFrameHad2Fingers = false;

  public boolean schedulePinchDeletion;

  public void init(Context context) {
    if (!initialized) {
      for (int i = 0; i < 10; i++) {
        touches.add(new Touch());
        frontTouches.add(new Touch());
      }

      scaleGestureDetector = new ScaleGestureDetector(context, new ScaleGestureDetector.SimpleOnScaleGestureListener() {
        @Override
        public boolean onScale(@NonNull ScaleGestureDetector detector) {
          if (pinch != null) {
            pinch.setFactor(detector.getScaleFactor());
            pinch.setInversalFactor(detector.getScaleFactor());
          }
          return super.onScale(detector);
        }

        @Override
        public boolean onScaleBegin(@NonNull ScaleGestureDetector detector) {
          pinch = new Pinch(1f);
          return true;
        }

        @Override
        public void onScaleEnd(@NonNull ScaleGestureDetector detector) {
          schedulePinchDeletion = true;
        }
      });

      rotationGestureDetector = new RotationGestureDetector(detector -> {
        if (pinchRotate == null) pinchRotate = new PinchRotate(0f);
        pinchRotate.setAngle(detector.getAngle());
      });

      initialized = true;
    }
  }

  public void onTouchEvent(MotionEvent event, WyrmSurface surface) {
    scaleGestureDetector.onTouchEvent(event);
    rotationGestureDetector.onTouchEvent(event);
    int pointerId = event.getPointerId(event.getActionIndex());
    float x = event.getX();
    float y = event.getY();

    frontTouches.get(pointerId).setPosition(new Vec2(x, y));
    if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
      int pointerCount = event.getPointerCount();

      for (int i = 0; i < pointerCount; i++) {
        int pointerId_2 = event.getPointerId(i);
        float x2 = event.getX(i);
        float y2 = event.getY(i);

        if (frontTouches.get(pointerId_2).getOldPosition().getX() >= 0f && frontTouches.get(pointerId_2).getOldPosition().getY() >= 0f) {
          frontTouches.get(pointerId_2).setSlide(new Vec2(
            x2 - frontTouches.get(pointerId_2).getOldPosition().getX(),
            y2 - frontTouches.get(pointerId_2).getOldPosition().getY()
          ));
        }
        frontTouches.get(pointerId_2).setOldPosition(new Vec2(x2, y2));
        frontTouches.get(pointerId_2).setPosition(new Vec2(x2, y2));
        frontTouches.get(pointerId_2).setPressed(true);
      }
    }
    if (event.getActionMasked() == MotionEvent.ACTION_MOVE) {
      int pointerCount = event.getPointerCount();
      for (int i = 0; i < pointerCount; i++) {
        var pointerId_2 = event.getPointerId(i);
        float x2 = event.getX(i);
        float y2 = event.getY(i);

        if (frontTouches.get(pointerId_2).getOldPosition().getX() >= 0f && frontTouches.get(pointerId_2).getOldPosition().getY() >= 0f) {
          frontTouches.get(pointerId_2).setSlide(new Vec2(
            x2 - frontTouches.get(pointerId_2).getOldPosition().getX(),
            y2 - frontTouches.get(pointerId_2).getOldPosition().getY()
          ));
        }
        frontTouches.get(pointerId_2).setOldPosition(new Vec2(x2, y2));
        frontTouches.get(pointerId_2).setPosition(new Vec2(x2, y2));
        frontTouches.get(pointerId_2).setPressed(true);
      }

      for (int i = 0; i < frontTouches.size(); i++) {

      }
    }

    if (event.getActionMasked() == MotionEvent.ACTION_UP) {
      int pointerCount = event.getPointerCount();
      for (int i = 0; i < pointerCount; i++) {
        int pointerId_2 = event.getPointerId(i);

        event.getX(i);
        event.getY(i);

        frontTouches.get(pointerId_2).setSlide(new Vec2(0f, 0f));
        frontTouches.get(pointerId_2).setOldPosition(new Vec2(-1f, -1f));
        frontTouches.get(pointerId_2).setPressed(false);
      }
    }

    mPreviousX = x;
    mPreviousY = y;
  }

  public void preFrame() {
    int i = 0;
    for (Touch touch : touches) {
      touch.setPressed(frontTouches.get(i).isPressed());

      if (touch.isLongPressed()) {
        touch.setLongPressed(false);
      } else if (touch.isPressed() && !touch.isLongSetted()) {
        touch.pressedTime += Time.deltaTime;
        if (touch.pressedTime >= (longClickMillis / 1000f)) {
          touch.setLongPressed(true);
          touch.setLongSetted(true);
        }
      }

      if (touch.isDown()) touch.setDown(false);
      else if (touch.isPressed() && !touch.isLongSetted()) {
        touch.setDownSetted(true);
        touch.setDown(true);
      }

      if (touch.isPressed()) touch.setUpSetted(false);
      else if (touch.isUp()) touch.setUp(false);
      else if (!touch.isUpSetted()) {
        touch.setUp(true);
        touch.setUpSetted(true);
      }

      if (!touch.isPressed()) {
        touch.setDownSetted(false);
        touch.setLongSetted(false);
        touch.pressedTime = 0f;
        if (!touch.isUp()) touch.setSlided(false);
      }

      touch.setPosition(frontTouches.get(i).getPosition());
      touch.setOldPosition(frontTouches.get(i).getOldPosition());
      touch.setSlide(frontTouches.get(i).getSlide());

      if (!touch.isSlided() && frontTouches.get(i).getSlide().length() >= slidingDeadZone) {
        touch.setSlided(true);
      }
      if (!touch.isPressed()) touch.setSlide(new Vec2(0f, 0f));

      i++;
    }

    if (!touches.get(0).isPressed() || !touches.get(0).isSlided()) {
      twoFingerSwipeFront = null;
      lastFrameHad2Fingers = false;
    } else if (!touches.get(1).isPressed() || !touches.get(1).isSlided()) {
      twoFingerSwipeFront = null;
      lastFrameHad2Fingers = false;
    } else {
      lastFrameHad2Fingers = true;
    }

    if (schedulePinchDeletion) {
      for (Touch touch : touches) {
        touch.setSlided(true);
        touch.setPressed(false);
        touch.setUp(false);
        touch.setDown(false);
        touch.setLongPressed(false);
        touch.setSlide(new Vec2(0f, 0f));
        touch.setOldPosition(new Vec2(-1f, -1f));
      }
      this.pinch = null;
      schedulePinchDeletion = false;
    }

    if (!touches.get(0).isPressed() && touches.get(1).isPressed()) {
      touches.get(1).setPressed(false);
    }
  }

  public void posFrame() {

  }

  public Touch getTouch(int index) {
    if (touches.size() > index) {
      return touches.get(index);
    } else return null;
  }

  public Key getKey(String name) {
    for (Key next : keys) {
      if (next.getName().equals(name)) {
        return next;
      }
    }
    return null;
  }

  public void setKey(String name, boolean pressed, boolean down, boolean up) {
    boolean updateKey = false;
    for (Key next : keys) {
      if (next.getName().equals(name)) {
        next.setPressed(pressed);
        next.setDown(down);
        next.setUp(up);
        updateKey = true;
      }
    }

    if (!updateKey) {
      registerKey(name, pressed, down, up);
    }
  }

  public void registerKey(String name, boolean pressed, boolean down, boolean up) {
    if (getKey(name) == null) {
      keys.add(new Key(name, pressed, down, up));
    }
  }

  private void detectTwoFingerSwipe(Vec2 initialVector, Vec2 comparisonVector) {
    twoFingerSwipeFront = new TwoFingerSwipe(initialVector, comparisonVector);
  }
}
