/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.core.renderer;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import androidx.annotation.NonNull;

import com.wyrm.engine.core.Core;
import com.wyrm.engine.core.components.color.Color;
import com.wyrm.engine.core.configs.ScreenConfig;
import com.wyrm.engine.core.cpp.renderer.ImGuiRenderer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;

public class WyrmRenderer implements GLSurfaceView.Renderer {

  private final Context context;

  private int width;
  private int height;

  private final WyrmSurface surface;

  private CoroutineDispatcher glDispatcher;

  public WyrmRenderer(@NonNull WyrmSurface surface) {
    this.surface = surface;
    this.context = surface.getContext();
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    Color color = Color.SKY_BLUE;
    GLES30.glClearColor(color.r, color.g, color.b, color.a);
    GLES30.glEnable(GLES30.GL_DEPTH_TEST);
    GLES30.glDepthFunc(GLES30.GL_LEQUAL);

    ImGuiRenderer.init(surface.getHolder().getSurface(), surface);

    Core.getInstance().onSurfaceCreated(this, context);
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    ScreenConfig.getInstance().setGlWidth(width);
    ScreenConfig.getInstance().setGlHeight(height);
    GLES30.glViewport(0, 0, width, height);

    ImGuiRenderer.surfaceChange(width, height);

    Core.getInstance().onSurfaceChanged(context, width, height);
    this.width = width;
    this.height = height;
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);

    ImGuiRenderer.mainLoop(surface);

    Core.getInstance().repeatEveryFrame(context, width, height);
  }

  public CoroutineDispatcher getGlDispatcher() {
    if (glDispatcher == null) {
      glDispatcher = new CoroutineDispatcher() {
        @Override
        public void dispatch(@NonNull CoroutineContext coroutineContext, @NonNull Runnable runnable) {
          surface.queueEvent(runnable);
        }
      };
    }
    return glDispatcher;
  }
}
