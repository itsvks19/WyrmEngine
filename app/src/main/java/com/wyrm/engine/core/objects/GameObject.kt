package com.wyrm.engine.core.objects

import android.content.Context
import com.wyrm.engine.core.components.Component
import com.wyrm.engine.core.engine.Engine
import java.io.Serial
import java.io.Serializable

open class GameObject(
  var name: String? = null
) : Serializable {
  companion object {
    @Serial
    private const val serialVersionUID = 1L
  }

  var transform: Transform = Transform()

  private val components = mutableListOf<Component>()

  constructor(transform: Transform) : this() {
    this.transform = transform
  }

  constructor(name: String, transform: Transform) : this(name) {
    this.transform = transform
  }

  internal fun onStart(context: Context, engine: Engine) {
    components.forEach { it.onStart() }
  }

  internal fun onRepeat(context: Context, engine: Engine) {
    components.forEach { it.onRepeat() }
  }

  fun findComponent(title: String?): Component? {
    return components.find { it.title == title }
  }

  fun addComponent(component: Component) {
    components.add(component)
  }

  fun getComponents(): List<Component> {
    return components
  }
}
