package com.wyrm.engine.core.renderer;

import android.content.Context;
import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

import com.wyrm.engine.core.Core;
import com.wyrm.engine.core.components.color.Color;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

public class WyrmRenderer implements GLSurfaceView.Renderer {

  private final Context context;

  private int width;
  private int height;

  public WyrmRenderer(Context context) {
    this.context = context;
  }

  @Override
  public void onSurfaceCreated(GL10 gl, EGLConfig config) {
    Color color = Color.SKY_BLUE;
    GLES30.glClearColor(color.r, color.g, color.b, color.a);
//    GLES30.glClearDepthf(50f);
    GLES30.glEnable(GLES30.GL_DEPTH_TEST);
//    GLES30.glDepthFunc(GLES30.GL_LEQUAL);
//    GLES30.glEnable(GLES30.GL_CULL_FACE);
//    GLES30.glCullFace(GLES30.GL_BACK);

    Core.getInstance().onSurfaceCreated(this, context);
  }

  @Override
  public void onSurfaceChanged(GL10 gl, int width, int height) {
    GLES30.glViewport(0, 0, width, height);
    Core.getInstance().onSurfaceChanged(context, width, height);
    this.width = width;
    this.height = height;
  }

  @Override
  public void onDrawFrame(GL10 gl) {
    GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT | GLES30.GL_DEPTH_BUFFER_BIT);
    Core.getInstance().repeatEveryFrame(context, width, height);
  }
}
