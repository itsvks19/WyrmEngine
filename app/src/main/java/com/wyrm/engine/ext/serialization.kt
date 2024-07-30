package com.wyrm.engine.ext

import com.google.gson.Gson
import java.io.Serializable

fun <T : Serializable> T.toJson(): String {
  return Gson().toJson(this)
}

inline fun <reified T : Serializable> String.fromJson(): T {
  return Gson().fromJson(this, T::class.java)
}