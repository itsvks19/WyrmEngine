package com.wyrm.engine;

public class Time {
  public static float deltaTime;
  private static float lastTime;

  static {
    deltaTime = 0;
    lastTime = (float) System.nanoTime();
  }

  public void addFrame() {
    float currentTime = System.nanoTime();
    deltaTime = (currentTime - lastTime) / 1_000_000_000f;
    lastTime = currentTime;
  }

  public static float systemTimeInSeconds() {
    return System.nanoTime() / 1_000_000_000f;
  }

  public static float getDeltaTime() {
    return deltaTime;
  }
}
