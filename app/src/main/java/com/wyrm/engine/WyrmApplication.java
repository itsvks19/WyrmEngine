package com.wyrm.engine;

import android.app.Application;

import com.wyrm.engine.core.Core;

public class WyrmApplication extends Application {
  @Override
  public void onCreate() {
    super.onCreate();
    Core.getInstance().onStart(getApplicationContext());
  }
}
