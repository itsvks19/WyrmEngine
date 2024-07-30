package com.wyrm.engine.core.components;

abstract public class Component {

  public String getTitle() {
    return getClass().getSimpleName();
  }

  public abstract void onStart();
  public abstract void onRepeat();
}
