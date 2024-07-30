package com.zyron.filetree.executors

import java.io.File
import java.security.MessageDigest

class FileCacheMap<K : File, V>(capacity: Int) {

  private companion object {
    const val INITIAL_CAPACITY = 1 shl 4
    const val MAXIMUM_CAPACITY = 1 shl 30
  }

  private var hashtable: Array<Entry<K, V>?> = arrayOfNulls(INITIAL_CAPACITY)
  private var digest: MessageDigest = MessageDigest.getInstance("MD5")

  init {
    digest = MessageDigest.getInstance("MD5")
    val cap = tableSizeFor(capacity)
    hashtable = arrayOfNulls(cap)
  }

  private fun tableSizeFor(cap: Int): Int {
    val n = -1 ushr Integer.numberOfLeadingZeros(cap - 1)
    return if (n < 0) 1 else if (n >= MAXIMUM_CAPACITY) MAXIMUM_CAPACITY else n + 1
  }

  private class Entry<K, V>(val key: K, var value: V) {
    var next: Entry<K, V>? = null
  }

  private fun hash(key: K): Int {
    val hash = digest.digest(key.toString().toByteArray())
    var result = 0
    for (i in 0 until 4) {
      result = result or ((hash[i].toInt() and 0xFF) shl (8 * i))
    }
    return result
  }

  fun put(key: K, value: V) {
    val hashCode = hash(key) and (hashtable.size - 1)
    var node = hashtable[hashCode]
    if (node == null) {
      hashtable[hashCode] = Entry(key, value)
    } else {
      var prevNode: Entry<K, V>? = null
      while (node != null) {
        if (node.key == key) {
          node.value = value
          return
        }
        prevNode = node
        node = node.next
      }
      prevNode?.next = Entry(key, value)
    }
  }

  fun get(key: K): V? {
    val hashCode = hash(key) and (hashtable.size - 1)
    var node = hashtable[hashCode]
    while (node != null) {
      if (node.key == key) {
        return node.value
      }
      node = node.next
    }
    return null
  }
}