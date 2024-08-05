package com.wyrm.engine.ui.colorpicker

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.wyrm.engine.R
import com.wyrm.engine.core.components.color.Color
import com.wyrm.engine.databinding.LayoutColorPickerBinding
import com.wyrm.engine.ext.colorToHexString
import com.wyrm.engine.ui.dialog.WyrmDialog

class ColorPicker @JvmOverloads constructor(
  context: Context,
  var defaultColor: Color = Color.WHITE,
  var onColorChanged: (Color) -> Unit = {}
) {
  private val dialog = WyrmDialog(context)
  private val binding = LayoutColorPickerBinding.inflate(LayoutInflater.from(context))

  var onColorPicked: (Color) -> Unit = {}

  init {
    val mColor = defaultColor.toColorInt()

    binding.apply {

      oldColor.apply {
        setOriginalColor(mColor)
        color = mColor

        setOnClickListener {
          colorPickerView.color = color
          newColor.color = color
          textLayout.name.apply {
            setText(color.colorToHexString().replace("#", ""))
          }
          onColorChanged(Color.fromColorInt(color))
        }
      }

      newColor.color = mColor

      colorPickerView.apply {
        color = mColor
        setOnColorChangedListener {
          val mNewColor = Color.fromColorInt(it)
          newColor.color = it
          textLayout.name.apply {
            setText(it.colorToHexString().replace("#", ""))
          }
          onColorChanged(mNewColor)
        }
      }

      textLayout.apply {
        nameLayout.apply {
          hint = "Hex Color"
          prefixText = "#"
          startIconDrawable = AppCompatResources.getDrawable(context, R.drawable.palette)
        }
        name.apply {
          setText(mColor.colorToHexString().replace("#", ""))

          filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            if (source.matches(Regex("[a-fA-F0-9]+"))) source else ""
          }, InputFilter.LengthFilter(8))

          doAfterTextChanged {
            validateColorInput(it, nameLayout)

            if (nameLayout.error == null) {
              val c = android.graphics.Color.parseColor("#${it.toString()}")
              colorPickerView.color = c
              newColor.color = c
            }
          }
        }
      }
    }

    dialog.apply {
      setLayout(
        WindowManager.LayoutParams.WRAP_CONTENT,
        WindowManager.LayoutParams.WRAP_CONTENT
      )
      setTitle("Color Picker")
      setMessage("Choose color")
      setView(binding.root)
      setPositiveButton("Confirm") { d, _ ->
        onColorPicked(Color.fromColorInt(binding.colorPickerView.color))
        d.dismiss()
      }
    }
  }

  fun show() {
    dialog.show()
  }

  private fun validateColorInput(s: Editable?, layout: TextInputLayout) {
    val colorHex = s.toString()
    layout.error = if (colorHex.matches(Regex("^([a-fA-F0-9]{6}|[a-fA-F0-9]{8})$"))) null
    else "Invalid color format."
  }
}