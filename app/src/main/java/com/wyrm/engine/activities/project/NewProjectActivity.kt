package com.wyrm.engine.activities.project

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VIDEO
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.doOnTextChanged
import com.blankj.utilcode.util.FileUtils
import com.blankj.utilcode.util.PermissionUtils
import com.wyrm.engine.Constants
import com.wyrm.engine.activities.EditorActivity
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.databinding.ActivityNewProjectBinding
import com.wyrm.engine.ext.encrypt
import com.wyrm.engine.ext.getBitmapFromAssets
import com.wyrm.engine.ext.open
import com.wyrm.engine.ext.toJson
import com.wyrm.engine.ext.toast
import com.wyrm.engine.managers.ProjectManager
import com.wyrm.engine.model.project.Project
import org.greenrobot.eventbus.EventBus
import java.io.File

class NewProjectActivity : BaseActivity<ActivityNewProjectBinding>(
  ActivityNewProjectBinding::inflate
) {
  private val pickMedia =
    registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
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
        val prFile = File("${Constants.PROJECTS_PATH}/${name.text}")
        FileUtils.createOrExistsDir(prFile)
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Scenes")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Assets")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Shaders")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Textures")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Audio")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Models")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Materials")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Animations")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Scripts")
        FileUtils.createOrExistsDir("${prFile.absolutePath}/Plugins")

        val iconFile = File("${prFile.absolutePath}/.settings/icon.image")
        FileUtils.createOrExistsDir(iconFile.parentFile)
        FileUtils.createOrExistsFile(iconFile)

        pickedImageUri?.let { uri ->
          contentResolver.openInputStream(uri)?.use { inputStream ->
            iconFile.outputStream().use { outputStream ->
              inputStream.copyTo(outputStream)
            }
          }
        } ?: assets.open("project/default_icon.jpg").use { inputStream ->
          BitmapFactory.decodeStream(inputStream).compress(
            Bitmap.CompressFormat.JPEG,
            100,
            iconFile.outputStream()
          )
        }

        toast("Project created successfully")

        val project = Project(prFile)
        File(prFile, ".wproject").writeText(project.toJson())
        EventBus.getDefault().post(project)
        ProjectManager.instance.openProject(project).also { open(EditorActivity::class.java) }
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
        .setMediaType(ActivityResultContracts.PickVisualMedia.ImageOnly)
        .build()
    )
  }
}
