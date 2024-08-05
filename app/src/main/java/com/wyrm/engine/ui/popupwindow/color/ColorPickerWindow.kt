package com.wyrm.engine.ui.popupwindow.color

import android.content.Context
import android.text.Editable
import android.text.InputFilter
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import com.google.android.material.textfield.TextInputLayout
import com.wyrm.engine.R
import com.wyrm.engine.core.components.color.Color
import com.wyrm.engine.core.components.color.Color.Companion.fromColorInt
import com.wyrm.engine.databinding.LayoutColorPickerBinding
import com.wyrm.engine.ext.colorToHexString
import com.wyrm.engine.ui.popupwindow.MoveablePopupWindow

class ColorPickerWindow @JvmOverloads constructor(
  context: Context,
  var defaultColor: Color = Color.SKY_BLUE,
  val onColorChanged: (Color) -> Unit = {}
) : MoveablePopupWindow<LayoutColorPickerBinding>(
  context,
  LayoutColorPickerBinding::inflate
) {
  private var onColorSelected: (Color) -> Unit = {}

  private var mOldColor = defaultColor.toColorInt()

  init {
    setupViews()
    isClippingEnabled = true
  }

  override fun setupViews() {
    setupMoveView(binding.root)

    binding.apply {
      textLayout.apply {
        name.apply {
          setText(mOldColor.colorToHexString().replace("#", ""))

          filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            if (source.matches(Regex("[a-fA-F0-9]+"))) source else ""
          }, InputFilter.LengthFilter(8))

          doAfterTextChanged {
            validateColorInput(it, nameLayout)

            if (nameLayout.error == null) {
              val c = android.graphics.Color.parseColor("#${it.toString()}")
              colorPickerView.color = c
              newColor.color = c
              onColorChanged(fromColorInt(c))
            }
          }
        }
        nameLayout.apply {
          hint = "Hex Color"
          prefixText = "#"
          startIconDrawable = AppCompatResources.getDrawable(context, R.drawable.palette)
        }
      }

      newColor.color = mOldColor

      oldColor.apply {
        color = mOldColor
        setOnClickListener {
          colorPickerView.setColor(mOldColor, true)
        }
      }
      colorPickerView.apply {
        color = mOldColor
        setOnColorChangedListener {
          val mNewColor = fromColorInt(it)
          newColor.color = it
          textLayout.name.apply {
            setText(it.colorToHexString().replace("#", ""))
          }
          onColorChanged(mNewColor)
        }
      }
    }

    setOnDismissListener {
      @ColorInt
      val currentColor = android.graphics.Color.parseColor(
        "#${binding.textLayout.name.text.toString()}"
      )
      val color = fromColorInt(currentColor)
      onColorSelected(color)
    }
  }

  private fun validateColorInput(s: Editable?, layout: TextInputLayout) {
    val colorHex = s.toString()
    layout.error = if (colorHex.matches(Regex("^([a-fA-F0-9]{6}|[a-fA-F0-9]{8})$"))) null
    else "Invalid color format."
  }

  fun setOnColorSelectedListener(onColorSelect: (Color) -> Unit) {
    this.onColorSelected = onColorSelect
  }
}