package com.wyrm.engine.core.cpp.renderer

import android.view.Surface
import com.wyrm.engine.core.renderer.WyrmSurface

object ImGuiRenderer {
  @JvmStatic
  external fun init(surface: Surface)

  @JvmStatic
  external fun mainLoop(wyrmSurface: WyrmSurface)

  @JvmStatic
  external fun surfaceChange(width: Int, height: Int)
}