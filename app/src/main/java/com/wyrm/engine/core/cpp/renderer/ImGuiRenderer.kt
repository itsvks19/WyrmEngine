/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.cpp.renderer

import android.view.Surface
import com.wyrm.engine.core.renderer.WyrmSurface

object ImGuiRenderer {
  @JvmStatic
  external fun init(surface: Surface, wyrmSurface: WyrmSurface)

  @JvmStatic
  external fun mainLoop(wyrmSurface: WyrmSurface)

  @JvmStatic
  external fun surfaceChange(width: Int, height: Int)

  @JvmStatic
  external fun destroy()
}