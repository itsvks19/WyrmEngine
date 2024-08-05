package com.wyrm.engine.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.wyrm.engine.databinding.LayoutTopBarMenuItemBinding
import com.wyrm.engine.model.MenuItem

class EditorTopBarMenuAdapter @JvmOverloads constructor(
  private val items: MutableList<MenuItem> = mutableListOf()
) : RecyclerView.Adapter<EditorTopBarMenuAdapter.VH>() {
  inner class VH(val binding: LayoutTopBarMenuItemBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return VH(
      LayoutTopBarMenuItemBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
      )
    )
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.binding.apply {
      title.text = items[position].title
      root.setOnClickListener { items[position].onClick(it) }
    }
  }
}