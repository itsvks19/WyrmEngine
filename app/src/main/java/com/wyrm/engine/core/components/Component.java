/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

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
