/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.ui.popupmenu

data class PopupMenuItem @JvmOverloads constructor(
  val menu: WyrmPopupMenu,
  var title: String,
  var subMenu: WyrmPopupMenu? = null,
  var onClick: (WyrmPopupMenu) -> Unit = {}
) {
  val hasSubMenu get() = subMenu != null
}