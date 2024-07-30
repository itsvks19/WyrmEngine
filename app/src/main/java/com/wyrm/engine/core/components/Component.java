package com.wyrm.engine.core.components;

import java.io.Serial;
import java.io.Serializable;

abstract public class Component implements Serializable {

  @Serial
  private static final long serialVersionUID = 2199135923503205124L;

  public String getTitle() {
    return getClass().getSimpleName();
  }

  public abstract void onStart();

  public abstract void onRepeat();
}
