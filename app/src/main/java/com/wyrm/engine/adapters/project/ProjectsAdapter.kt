package com.wyrm.engine.adapters.project

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.ToastUtils
import com.wyrm.engine.activities.EditorActivity
import com.wyrm.engine.databinding.LayoutProjectItemBinding
import com.wyrm.engine.ext.open
import com.wyrm.engine.ext.setIcon
import com.wyrm.engine.model.project.Project

class ProjectsAdapter(
  private val projects: MutableList<Project> = mutableListOf()
) : RecyclerView.Adapter<ProjectsAdapter.VH>() {
  inner class VH(val binding: LayoutProjectItemBinding) : RecyclerView.ViewHolder(binding.root)

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
    return VH(
      LayoutProjectItemBinding.inflate(
        LayoutInflater.from(parent.context),
        parent,
        false
      )
    )
  }

  override fun getItemCount() = projects.size

  @SuppressLint("SetTextI18n")
  override fun onBindViewHolder(holder: VH, position: Int) {
    val project = projects[position]

    holder.binding.apply {
      if (project.icon.isExists) icon.setIcon(project.icon)
      name.text = project.name
      name.isSelected = true
      version.text = "v${project.engineVersion}"

      menu.setOnClickListener { ToastUtils.showShort("Menu") }

      root.setOnClickListener {
        ToastUtils.showShort(project.name)
        it.context.open(EditorActivity::class.java)
      }
    }
  }
}