package com.wyrm.engine;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.wyrm.engine.core.Core;

public class WyrmApplication extends Application {
  private int counterActivityCreate = 0;

  @Override
  public void onCreate() {
    super.onCreate();
    Core.getInstance().onStart(getApplicationContext());

    registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
      @Override
      public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
        counterActivityCreate++;
      }

      @Override
      public void onActivityStarted(@NonNull Activity activity) {
      }

      @Override
      public void onActivityResumed(@NonNull Activity activity) {
      }

      @Override
      public void onActivityPaused(@NonNull Activity activity) {
      }

      @Override
      public void onActivityStopped(@NonNull Activity activity) {
      }

      @Override
      public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {
      }

      @Override
      public void onActivityDestroyed(@NonNull Activity activity) {
        counterActivityCreate--;
        if (counterActivityCreate == 0) {
          Core.getInstance().destroyCore();
        }
      }
    });
  }

  @Override
  public void onTerminate() {
    super.onTerminate();
    Core.getInstance().destroyCore();
  }
}
