package com.wyrm.engine.adapters.graphics

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.updateLayoutParams
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.wyrm.engine.core.objects.GameObject
import com.wyrm.engine.databinding.LayoutObjectListItemBinding
import com.wyrm.engine.ext.doIf
import com.wyrm.engine.ext.dp

class ObjectListAdapter(
  private val items: MutableList<GameObject> = mutableListOf()
) : RecyclerView.Adapter<ObjectListAdapter.VH>() {
  inner class VH(
    val binding: LayoutObjectListItemBinding
  ) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return VH(
      LayoutObjectListItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun getItemCount() = items.size

  override fun onBindViewHolder(holder: VH, position: Int) {
    holder.binding.apply {
      doIf(position == 0) {
        name.updateLayoutParams<ConstraintLayout.LayoutParams> {
          topMargin = 8.dp as Int
        }
      }

      name.text = items[position].name
      root.setOnClickListener { ToastUtils.showShort(name.text) }
    }
  }
}