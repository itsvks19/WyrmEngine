package com.wyrm.engine.ext

import java.util.LinkedList

fun <T> MutableList<T>.find(predicate: Boolean) = this.find { predicate }

fun <E> linkedListOf() = LinkedList<E>()