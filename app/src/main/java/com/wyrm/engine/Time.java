package com.wyrm.engine;

public class Time {
  public static float deltaTime;
  private float dt = 0f;
  private long startTime = System.nanoTime();

  public void addFrame() {
    dt = (float) System.nanoTime() - startTime;
    startTime = System.nanoTime();
    dt /= 1.0E9f;
    deltaTime = getDeltaTime();
  }

  public float getDeltaTime() {
    return dt;
  }
}
