/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.adapters.popupmenu

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wyrm.engine.databinding.LayoutWyrmPopupMenuItemBinding
import com.wyrm.engine.ext.dp
import com.wyrm.engine.ui.popupmenu.PopupMenuItem

class WyrmPopupMenuAdapter @JvmOverloads constructor(
  private val items: MutableList<PopupMenuItem> = mutableListOf()
) : RecyclerView.Adapter<WyrmPopupMenuAdapter.VH>() {
  inner class VH(
    val binding: LayoutWyrmPopupMenuItemBinding
  ) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return VH(
      LayoutWyrmPopupMenuItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.binding.apply {
      menuTitle.text = items[position].title
      chevronRight.visibility = if (items[position].hasSubMenu) View.VISIBLE else View.INVISIBLE

      root.setOnClickListener {
        val item = items[position]
        if (item.hasSubMenu) {
          // item.subMenu?.show(it, 141.dp.toInt(), -27.dp.toInt())
          item.subMenu?.show(it, yoff = -27.dp.toInt())
        }
        item.onClick(item.menu).also { item.menu.dismiss() }
      }
    }
  }

  fun addItem(popupMenuItem: PopupMenuItem) {
    items.add(popupMenuItem)
    notifyItemInserted(items.indexOf(popupMenuItem))
  }
}