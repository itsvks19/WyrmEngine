package com.wyrm.engine.model

import android.view.View

data class MenuItem @JvmOverloads constructor(
  val title: String,
  val onClick: (View) -> Unit = {}
)
