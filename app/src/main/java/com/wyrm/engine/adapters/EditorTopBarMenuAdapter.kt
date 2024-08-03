package com.wyrm.engine.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.wyrm.engine.activities.CodeActivity
import com.wyrm.engine.databinding.LayoutTopBarMenuItemBinding
import com.wyrm.engine.model.MenuItem

class EditorTopBarMenuAdapter @JvmOverloads constructor(
  private val items: MutableList<MenuItem> = mutableListOf(),
  private val context: Context
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
      root.setOnClickListener {
        //since theres no id to compare against
        when(title.text){
          "Code" -> {
            context.startActivity(Intent(context, CodeActivity::class.java))
          }
          else -> {ToastUtils.showShort(title.text)}
        }
      }
    }
  }
}