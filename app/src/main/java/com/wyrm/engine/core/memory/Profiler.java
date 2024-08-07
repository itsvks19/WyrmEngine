/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.memory;

import com.wyrm.engine.Time;
import com.wyrm.engine.core.Core;
import com.wyrm.engine.ext.Utils;

public class Profiler {
  public static int frameLimit = 60;
  public static int frameRate = 0;
  public static float frameTime = 0f;

  public static Memory memory;

  private static int frames = 0;
  private static long startTime = System.nanoTime();

  public static void update() {
    if (memory == null) memory = new Memory();
    addFrame();
    memory.update();
  }

  public static void addFrame() {
    frames++;
    if (System.nanoTime() - startTime >= 1_000_000_000) {
      frameRate = frames;
      frames = 0;
      startTime = System.nanoTime();
      frameTime = Utils.toDecimals(Time.getDeltaTime() * 1000f, 2);
    }
  }

  public static void logFrame() {
    Core.getInstance().console.log("FPS: " + frameRate);
  }
}
