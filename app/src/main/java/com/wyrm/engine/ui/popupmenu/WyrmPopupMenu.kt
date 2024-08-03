package com.wyrm.engine.ui.popupmenu

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.R.attr
import com.wyrm.engine.adapters.popupmenu.WyrmPopupMenuAdapter
import com.wyrm.engine.databinding.LayoutWyrmPopupMenuBinding
import com.wyrm.engine.ext.dp
import com.wyrm.engine.ext.getThemeColor

@SuppressLint("ClickableViewAccessibility")
data class WyrmPopupMenu @JvmOverloads constructor(
  var attachableView: View,
  val items: MutableList<PopupMenuItem> = mutableListOf(),
  val onDismiss: () -> Unit = {},
) {
  private val context = attachableView.context
  private var binding = LayoutWyrmPopupMenuBinding.inflate(LayoutInflater.from(context))

  private val window = PopupWindow(context).apply {
    width = 140.dp.toInt()
    height = WindowManager.LayoutParams.WRAP_CONTENT
    isFocusable = true
    setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    elevation = 5f
  }

  private val adapter = WyrmPopupMenuAdapter(items)

  init {
    window.contentView = binding.root
    binding.apply {
      root.background = GradientDrawable().apply {
        shape = GradientDrawable.RECTANGLE
        cornerRadius = 5f.dp.toFloat()
        setColor(context.getThemeColor(attr.colorSurface))
        setStroke(1, context.getThemeColor(attr.colorOutline))
      }
      root.setOnTouchListener { _, event ->
        when (event.actionMasked) {
          MotionEvent.ACTION_OUTSIDE -> {
            dismiss()
            true
          }

          else -> false
        }
      }

      popupMenuItems.apply {
        layoutManager = LinearLayoutManager(
          context,
          LinearLayoutManager.VERTICAL,
          false
        )
        adapter = this@WyrmPopupMenu.adapter
      }
    }
  }

  fun addMenuItem(item: PopupMenuItem) {
    adapter.addItem(item)
  }

  @JvmOverloads
  fun addMenuItem(
    title: String,
    subMenu: WyrmPopupMenu? = null,
    onClickListener: (WyrmPopupMenu) -> Unit = {}
  ) {
    addMenuItem(PopupMenuItem(this, title, subMenu, onClickListener))
  }

  @JvmOverloads
  fun show(
    view: View = attachableView,
    xoff: Int = 0,
    yoff: Int = 0,
    gravity: Int = Gravity.TOP or Gravity.START
  ) {
    window.showAsDropDown(view, xoff, yoff, gravity)
  }

  fun dismiss() {
    window.dismiss().also { onDismiss() }
  }
}