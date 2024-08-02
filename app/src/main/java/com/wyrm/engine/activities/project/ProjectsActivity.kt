package com.wyrm.engine.activities.project

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.adapters.project.ProjectsAdapter
import com.wyrm.engine.databinding.ActivityProjectsBinding
import com.wyrm.engine.ext.open
import com.wyrm.engine.managers.ProjectManager

class ProjectsActivity : BaseActivity<ActivityProjectsBinding>(ActivityProjectsBinding::inflate) {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    binding.projectsList.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(this@ProjectsActivity, 4)
      adapter = ProjectsAdapter(ProjectManager.instance.projects.toMutableList())
    }

    binding.addProject.setOnClickListener { open(NewProjectActivity::class.java) }
  }
}