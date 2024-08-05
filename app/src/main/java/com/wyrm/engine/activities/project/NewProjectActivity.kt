package com.wyrm.engine.activities.project

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.PickVisualMedia
import androidx.core.widget.doOnTextChanged
import com.blankj.utilcode.util.PermissionUtils
import com.wyrm.engine.activities.EditorActivity
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.databinding.ActivityNewProjectBinding
import com.wyrm.engine.ext.getBitmapFromAssets
import com.wyrm.engine.ext.open
import com.wyrm.engine.ext.toast
import com.wyrm.engine.managers.ProjectManager
import org.greenrobot.eventbus.EventBus

class NewProjectActivity : BaseActivity<ActivityNewProjectBinding>(
  ActivityNewProjectBinding::inflate
) {
  private val pickMedia =
    registerForActivityResult(PickVisualMedia()) { uri ->
      uri?.let {
        binding.icon.setImageURI(it).also {
          pickedImageUri = uri
        }
      } ?: toast("No media selected")
    }

  private val requestPermissions =
    registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { results ->
      if (results.values.all { it }) launchPhotoPicker()
      else results.entries.filter { !it.value }.forEach { toast("Permission denied: ${it.key}") }
    }

  private var pickedImageUri: Uri? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding.apply {
      icon.setImageBitmap(getBitmapFromAssets("project/default_icon.jpg"))

      cancel.setOnClickListener {
        open(ProjectsActivity::class.java)
        finish()
      }

      fun checkProjectExists(name: String) {
        ProjectManager.instance.projects.forEach {
          if (it.name == name) {
            nameLayout.error = "Project already exists"
            create.isEnabled = false
          }
        }
      }
      checkProjectExists(name.text.toString())

      name.doOnTextChanged { text, _, _, _ ->
        create.isEnabled = !text.isNullOrEmpty()
        nameLayout.error = if (text.isNullOrEmpty()) "Project name cannot be empty" else null
        checkProjectExists(text.toString())
      }

      selectIcon.setOnClickListener {
        when {
          Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE -> handlePermissions(
            READ_MEDIA_IMAGES,
            READ_MEDIA_VIDEO,
            READ_MEDIA_VISUAL_USER_SELECTED
          )

          Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> handlePermissions(
            READ_MEDIA_IMAGES,
            READ_MEDIA_VIDEO
          )

          else -> handlePermissions(READ_EXTERNAL_STORAGE)
        }
      }

      create.setOnClickListener {
        val project = ProjectManager.createProject(
          name.text.toString(),
          pickedImageUri?.let {
            contentResolver.openInputStream(it)
          } ?: assets.open("project/default_icon.jpg")
        )
        EventBus.getDefault().post(project)
        ProjectManager.instance.openProject(project).also { open(EditorActivity::class.java) }
        toast("Project created successfully")
        finish()
      }
    }
  }

  private fun handlePermissions(vararg permissions: String) {
    if (permissions.all { PermissionUtils.isGranted(it) }) launchPhotoPicker()
    else requestPermissions.launch(permissions.toList().toTypedArray())
  }

  private fun launchPhotoPicker() {
    pickMedia.launch(
      PickVisualMediaRequest.Builder()
        .setMediaType(PickVisualMedia.ImageOnly)
        .build()
    )
  }
}
