package com.wyrm.engine.ext

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.Serializable

fun <T : Serializable> T.toJson(): String {
  val gson = GsonBuilder().setPrettyPrinting().create()
  return gson.toJson(this)
}

inline fun <reified T : Serializable> String.fromJson(): T {
  return Gson().fromJson(this, T::class.java)
}