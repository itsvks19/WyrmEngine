package com.wyrm.engine.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ToastUtils;
import com.wyrm.engine.Time;
import com.wyrm.engine.activities.EditorActivity;
import com.wyrm.engine.core.components.console.Console;
import com.wyrm.engine.core.components.input.Input;
import com.wyrm.engine.core.components.mesh.MeshRenderer;
import com.wyrm.engine.core.memory.Profiler;
import com.wyrm.engine.core.renderer.WyrmRenderer;
import com.wyrm.engine.core.renderer.WyrmSurface;
import com.wyrm.engine.ext.ThreadingKt;

public class Core {
  @SuppressLint("StaticFieldLeak")
  private static Core instance;

  private Context context;
  private Context editorContext;
  private EditorActivity editorActivity;

  public Input input = new Input();
  public Time time = new Time();
  public Console console = new Console();

  public boolean isStarted = false;
  public boolean isGlContextCreated = false;

  private MeshRenderer meshRenderer;

  public static Core getInstance() {
    if (instance == null) {
      instance = new Core();
    }
    return instance;
  }

  public void onStart(Context context) {
    this.context = context;
    ToastUtils.showShort("Core Started...");
  }

  public void onStartEngine(Context context, EditorActivity activity) {
    this.editorContext = context;
    this.editorActivity = activity;
    input.init(context);

    meshRenderer = new MeshRenderer();

    isStarted = true;
  }

  public void destroy() {
    editorContext = null;
    editorActivity = null;
    instance = null;
  }

  public void destroyCore() {
    context = null;
  }

  public void onSurfaceCreated(WyrmRenderer renderer, Context surfaceContext) {
    isGlContextCreated = true;
    meshRenderer.onStart();
  }

  public void repeatEveryFrame(Context surfaceContext, int width, int height) {
    time.addFrame();
    input.preFrame();
    meshRenderer.onRepeat();
    input.posFrame();
    Profiler.update();

    ThreadingKt.runOnUiThread(() -> {
      if (editorActivity != null) {
        editorActivity.updateFov(meshRenderer.getCamera().getZoom());
        editorActivity.updateUiOnRepeat();
      }
      return null;
    });
  }

  public void onTouchEvent(@NonNull MotionEvent event, WyrmSurface surface) {
    editorActivity.handleButtonTouch(meshRenderer.getCamera());
    input.onTouchEvent(event, surface);
  }

  public void onScroll(float distanceX, float distanceY, int width, int height) {
    float rotationX = distanceX / width;
    float rotationY = distanceY / height;

    console.log("rotationX: " + rotationX);
    console.log("rotationY: " + rotationY);

    meshRenderer.getCamera().processTouchMovement(-rotationX, rotationY);
  }

  public void onScale(float scaleFactor) {
    console.log("scaleFactor: " + scaleFactor);
    meshRenderer.getCamera().processZoom(scaleFactor);
  }

  public void onSurfaceChanged(Context surfaceContext, int width, int height) {
    meshRenderer.onChanged();
  }

  public Context getContext() {
    if (context == null) {
      throw new RuntimeException("Call onStart() before getting application context");
    }
    return context;
  }

  public Context getEditorContext() {
    if (editorContext == null) {
      throw new RuntimeException("Call onStartEngine() before getting editorContext");
    }
    return editorContext;
  }

  public EditorActivity getEditorActivity() {
    if (editorActivity == null) {
      throw new RuntimeException("Call onStartEngine() before getting editorActivity");
    }
    return editorActivity;
  }
}
