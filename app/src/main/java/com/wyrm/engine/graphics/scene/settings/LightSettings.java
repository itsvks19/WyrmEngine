package com.wyrm.engine.graphics.scene.settings;

import com.wyrm.engine.core.components.color.Color;

import java.io.Serial;
import java.io.Serializable;

public class LightSettings implements Serializable {
  @Serial
  private static final long serialVersionUID = 7479736039115812690L;

  private Color ambientColor = Color.SPACE_BLACK;
  private Color spaceColor = Color.SKY_BLUE;

  public Color getAmbientColor() {
    return ambientColor;
  }

  public void setAmbientColor(Color ambientColor) {
    this.ambientColor = ambientColor;
  }

  public Color getSpaceColor() {
    return spaceColor;
  }

  public void setSpaceColor(Color spaceColor) {
    this.spaceColor = spaceColor;
  }
}
