package com.wyrm.engine.ext

import android.graphics.BitmapFactory
import android.widget.ImageView
import com.wyrm.engine.model.project.Icon

fun ImageView.setIcon(icon: Icon) {
  setImageBitmap(BitmapFactory.decodeFile(icon.path))
}