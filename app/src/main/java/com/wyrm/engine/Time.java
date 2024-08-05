package com.wyrm.engine;

import org.jetbrains.annotations.Contract;

public class Time {
  private static float deltaTime;
  private static double lastTime;

  static {
    deltaTime = 0;
    lastTime = System.nanoTime() / 1_000_000_000.0; // Initial time in seconds
  }

  public static void addFrame() {
    double currentTime = System.nanoTime() / 1_000_000_000.0; // Current time in seconds
    deltaTime = (float) (currentTime - lastTime); // Calculate delta time
    lastTime = currentTime; // Update last time
  }

  @Contract(pure = true)
  public static float getDeltaTime() {
    return deltaTime;
  }

  public static float systemTimeInSeconds() {
    return (float) (System.nanoTime() / 1_000_000_000.0); // Convert nanoseconds to seconds
  }
}
