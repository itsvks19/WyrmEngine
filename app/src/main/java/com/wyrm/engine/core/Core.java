package com.wyrm.engine.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.wyrm.engine.Time;
import com.wyrm.engine.activities.EditorActivity;
import com.wyrm.engine.core.components.console.Console;
import com.wyrm.engine.core.components.input.InputManager;
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

  public InputManager inputManager = new InputManager();
  public Console console = new Console();

  public boolean isStarted = false;
  public boolean isGlContextCreated = false;

  public static Core getInstance() {
    if (instance == null) {
      instance = new Core();
    }
    return instance;
  }

  public void onStart(Context context) {
    this.context = context;
  }

  public void onStartEngine(Context context, EditorActivity activity) {
    this.editorContext = context;
    this.editorActivity = activity;
    inputManager.init(context);

    isStarted = true;
  }

  public void destroy() {
    editorContext = null;
    editorActivity = null;
  }

  public void destroyCore() {
    context = null;
  }

  public void onSurfaceCreated(WyrmRenderer renderer, Context surfaceContext) {
    isGlContextCreated = true;
  }

  public void repeatEveryFrame(Context surfaceContext, int width, int height) {
    Time.addFrame();
    Profiler.update();
    inputManager.preFrame();

    ThreadingKt.runOnUiThread(() -> {
      if (editorActivity != null) {
        editorActivity.updateUiOnRepeat();
      }
      return null;
    });
  }

  public void onTouchEvent(@NonNull MotionEvent event, WyrmSurface surface) {
    inputManager.onTouchEvent(event);
  }

  public void onScroll(float distanceX, float distanceY, int width, int height) {
    float rotationX = distanceX / width;
    float rotationY = distanceY / height;

    //// DO SOMETHING
  }

  public void onScale(float scaleFactor) {
    //// DO SOMETHING
  }

  public void onSurfaceChanged(Context surfaceContext, int width, int height) {
    //// DO SOMETHING
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
