package com.wyrm.engine.ext

import android.widget.ImageView
import com.wyrm.engine.model.project.Icon

fun ImageView.setIcon(icon: Icon) {
  setImageIcon(android.graphics.drawable.Icon.createWithFilePath(icon.path))
}