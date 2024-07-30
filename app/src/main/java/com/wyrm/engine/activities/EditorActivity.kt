package com.wyrm.engine.activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.WindowManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.wyrm.engine.Constants
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.adapters.EditorTopBarMenuAdapter
import com.wyrm.engine.core.Core
import com.wyrm.engine.databinding.ActivityEditorBinding
import com.wyrm.engine.model.MenuItem

@SuppressLint("SetTextI18n")
class EditorActivity : BaseActivity<ActivityEditorBinding>(ActivityEditorBinding::inflate) {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
    Core.getInstance().onStartEngine(this, this)

    binding.topBar.apply {
      val menu = mutableListOf(
        MenuItem("File"),
        MenuItem("Edit"),
        MenuItem("View"),
        MenuItem("Code"),
        MenuItem("Build"),
      )
      dropdownMenu.apply {
        layoutManager = LinearLayoutManager(
          this@EditorActivity,
          LinearLayoutManager.HORIZONTAL,
          false
        )
        adapter = EditorTopBarMenuAdapter(menu)
      }

      info.apply {
        text = "v${Constants.APP_VERSION}"
        setOnClickListener { }
      }
    }
  }

  fun updateInfoText(text: String) {
    binding.topBar.info.text = text
  }

  override fun onDestroy() {
    super.onDestroy()
    Core.getInstance().destroy()
  }
}