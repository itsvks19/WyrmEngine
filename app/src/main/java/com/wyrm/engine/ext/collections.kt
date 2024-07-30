package com.wyrm.engine.ext

fun <T> MutableList<T>.find(predicate: Boolean) = this.find { predicate }