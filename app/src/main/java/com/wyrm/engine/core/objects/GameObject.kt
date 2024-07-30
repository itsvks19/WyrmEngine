package com.wyrm.engine.core.objects

import com.wyrm.engine.core.components.Component

open class GameObject(
  var name: String? = null
) {
  var transform: Transform = Transform()

  private val components = mutableListOf<Component>()

  constructor(transform: Transform) : this() {
    this.transform = transform
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
