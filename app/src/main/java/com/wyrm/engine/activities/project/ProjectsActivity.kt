package com.wyrm.engine.activities.project

import android.os.Bundle
import android.text.Html
import android.view.WindowManager
import androidx.recyclerview.widget.GridLayoutManager
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.SPStaticUtils
import com.wyrm.engine.R
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.adapters.project.ProjectsAdapter
import com.wyrm.engine.databinding.ActivityProjectsBinding
import com.wyrm.engine.ext.open
import com.wyrm.engine.managers.ProjectManager
import com.wyrm.engine.ui.dialog.WyrmDialog

class ProjectsActivity : BaseActivity<ActivityProjectsBinding>(ActivityProjectsBinding::inflate) {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    showEulaDialogIfRequired {
      binding.projectsList.apply {
        setHasFixedSize(true)
        layoutManager = GridLayoutManager(this@ProjectsActivity, 4)
        adapter = ProjectsAdapter(ProjectManager.instance.projects.toMutableList())
      }

      binding.addProject.setOnClickListener { open(NewProjectActivity::class.java) }
    }
  }

  private fun showEulaDialogIfRequired(runAfter: () -> Unit) {
    if (!SPStaticUtils.getBoolean("eula_accepted", false)) {
      WyrmDialog(this).apply {
        setTitle(R.string.eula_title)
        setMessage(Html.fromHtml(getString(R.string.eula_content), Html.FROM_HTML_MODE_LEGACY))
        setPositiveButton(R.string.accept) { dialog, _ ->
          dialog.dismiss()
          SPStaticUtils.put("eula_accepted", true)
          runAfter()
        }
        setCancelable(false)
        setNegativeButton(R.string.exit) { dialog, _ ->
          dialog.dismiss()
          AppUtils.exitApp()
        }
        setLayout(
          WindowManager.LayoutParams.WRAP_CONTENT,
          WindowManager.LayoutParams.WRAP_CONTENT
        )
        show()
      }
    } else runAfter()
  }
}