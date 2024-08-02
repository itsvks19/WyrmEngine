package com.wyrm.engine.activities.project

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.wyrm.engine.Constants
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.adapters.project.ProjectsAdapter
import com.wyrm.engine.databinding.ActivityProjectsBinding
import com.wyrm.engine.ext.open
import com.wyrm.engine.model.project.Project
import java.io.File

class ProjectsActivity : BaseActivity<ActivityProjectsBinding>(ActivityProjectsBinding::inflate) {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    val projects = mutableListOf(
      Project(File("${Constants.PROJECTS_PATH}/Test")),
      Project(File("${Constants.PROJECTS_PATH}/Tesbdxvbjxhvbxnbvfgdfgsgxcvdsgvxndbxvjxhbvxvmnxfgvjxhvbxnvbnt")),
      Project(File("${Constants.PROJECTS_PATH}/Test")),
    )

    binding.projectsList.apply {
      setHasFixedSize(true)
      layoutManager = GridLayoutManager(this@ProjectsActivity, 4)
      adapter = ProjectsAdapter(projects)
    }

    binding.addProject.setOnClickListener { open(NewProjectActivity::class.java) }
  }
}