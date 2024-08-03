package com.wyrm.engine.ui.popupmenu

data class PopupMenuItem @JvmOverloads constructor(
  val menu: WyrmPopupMenu,
  var title: String,
  var subMenu: WyrmPopupMenu? = null,
  var onClick: (WyrmPopupMenu) -> Unit = {}
) {
  val hasSubMenu get() = subMenu != null
}