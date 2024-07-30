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
import com.wyrm.engine.core.memory.Profiler;
import com.wyrm.engine.core.renderer.WyrmRenderer;
import com.wyrm.engine.core.renderer.WyrmSurface;
import com.wyrm.engine.ext.ThreadingKt;
import com.wyrm.engine.ext.Utils;
import com.wyrm.engine.graphics.Camera;
import com.wyrm.engine.graphics.shapes.Plane;

public class Core {
  @SuppressLint("StaticFieldLeak")
  private static Core instance;

  private Context context;
  private Context editorContext;
  private EditorActivity editorActivity;
  private Camera camera;

  private Plane plane = new Plane();

  public Input input = new Input();
  public Time time = new Time();
  public Console console = new Console();

  public boolean isStarted = false;
  public boolean isGlContextCreated = false;

  private boolean runFirstTimeOnRepeat = true;

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

    isStarted = true;
  }

  public void destroy() {
    context = null;
    editorContext = null;
    editorActivity = null;
    instance = null;
  }

  public void onSurfaceCreated(WyrmRenderer renderer, Context surfaceContext) {
    camera = new Camera();
    plane.setup(context);

    isGlContextCreated = true;
  }

  public void repeatEveryFrame(Context surfaceContext, int width, int height) {
    input.preFrame();

    if (input.getTouch(1).isDown()) {
      ToastUtils.showShort("down");
      console.log("down");
    }

    input.posFrame();
    Profiler.update();
    time.addFrame();

    ThreadingKt.runOnUiThread(() -> {
      editorActivity.updateInfoText(
        "FPS: " + Utils.toDecimals(Profiler.frameRate, 2)
      );

      return null;
    });
  }

  public void onTouchEvent(@NonNull MotionEvent event, WyrmSurface surface) {
    input.onTouchEvent(event, surface);
  }

  public void onSurfaceChanged(Context surfaceContext, int width, int height) {
    ToastUtils.showShort(width + "x" + height);
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
