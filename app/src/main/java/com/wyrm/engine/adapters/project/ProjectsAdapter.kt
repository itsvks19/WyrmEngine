package com.wyrm.engine.adapters.project

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.wyrm.engine.activities.EditorActivity
import com.wyrm.engine.databinding.LayoutProjectItemBinding
import com.wyrm.engine.databinding.LayoutTextInputBinding
import com.wyrm.engine.ext.open
import com.wyrm.engine.ext.setIcon
import com.wyrm.engine.ext.toast
import com.wyrm.engine.managers.ProjectManager
import com.wyrm.engine.model.project.Project
import com.wyrm.engine.ui.dialog.WyrmDialog
import com.wyrm.engine.ui.popupmenu.WyrmPopupMenu
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import java.io.File

class ProjectsAdapter(
  private val projects: MutableList<Project> = mutableListOf()
) : RecyclerView.Adapter<ProjectsAdapter.VH>() {
  inner class VH(val binding: LayoutProjectItemBinding) : RecyclerView.ViewHolder(binding.root)

  init {
    EventBus.getDefault().register(this)
  }

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

      menu.setOnClickListener { view ->
        WyrmPopupMenu(view).apply {
          addMenuItem("Rename") { renameProject(view.context, project, position) }
          addMenuItem("Delete") { deleteProject(view.context, project) }
          addMenuItem("Open in editor") {
            ProjectManager.instance.openProject(project)
            view.context.open(EditorActivity::class.java)
          }
        }.also { it.show() }
      }

      root.setOnClickListener {
        ProjectManager.instance.openProject(project)
        it.context.open(EditorActivity::class.java)
      }
    }
  }

  override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
    EventBus.getDefault().unregister(this)
  }

  private fun renameProject(context: Context, project: Project, position: Int) {
    WyrmDialog(context).apply {
      val oldName = project.name
      setTitle("Rename project")
      setMessage("Enter new project name")

      val inputBinding = LayoutTextInputBinding.inflate(
        LayoutInflater.from(context)
      )

      inputBinding.apply {
        name.apply {
          setText(oldName)
          doAfterTextChanged {
            val newText = it.toString()
            nameLayout.error = if (it.isNullOrEmpty()) {
              "Project name cannot be empty"
            } else if (newText != oldName && projects.any { p -> p.name == newText }) {
              "Project with name $text already exists"
            } else null

            setButtonEnabled(
              WyrmDialog.ButtonType.POSITIVE,
              nameLayout.error.isNullOrEmpty()
            )
          }
        }
      }

      setView(inputBinding.root)
      setPositiveButton("Rename") { d, _ ->
        val newName = inputBinding.name.text.toString()
        if (newName.isNotEmpty()) {
          val oldFile = project.file
          val newFile = File("${oldFile.parent}/$newName")
          if (oldFile.renameTo(newFile)) {
            val newProject = project.rename(newFile)
            projects[position] = newProject
            notifyItemChanged(position)
            context.toast("Project renamed from ${oldFile.name} to $newName")
          } else {
            context.toast("Failed to rename project")
          }
        }
        d.dismiss()
      }
      setCancelable(false)
      setNegativeButton("Cancel") { d, _ -> d.dismiss() }
      show()
    }
  }

  private fun deleteProject(context: Context, project: Project) {
    WyrmDialog(context).apply {
      setTitle("Delete project")
      setMessage("Do you want to delete this project?")
      setPositiveButton("Delete") { d, _ ->
        if (ProjectManager.instance.deleteProject(project)) {
          notifyItemRemoved(projects.indexOf(project))
          projects.remove(project)
          context.toast("Project deleted")
        }
        d.dismiss()
      }
      setNegativeButton("Cancel") { d, _ -> d.dismiss() }
      show()
    }
  }

  // Called from NewProjectActivity
  @Subscribe(threadMode = ThreadMode.MAIN)
  fun onCreateNewProject(project: Project) {
    projects.add(project)
    notifyItemInserted(projects.indexOf(project))
  }
}