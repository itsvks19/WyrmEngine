/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.zyron.filetree.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.textview.MaterialTextView

import com.zyron.filetree.R

class FileTreeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val chevronIconView: ShapeableImageView = itemView.findViewById(R.id.chevronIconView)
    val fileIconView: ShapeableImageView = itemView.findViewById(R.id.fileIconView)
    val fileNameView: MaterialTextView = itemView.findViewById(R.id.fileNameView)
}