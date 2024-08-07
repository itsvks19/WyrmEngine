/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.zyron.filetree.provider

import androidx.annotation.DrawableRes
import java.io.File

interface FileTreeIconProvider {

    @DrawableRes
    fun getChevronExpandIcon(): Int
    
    @DrawableRes
    fun getChevronCollapseIcon(): Int
    
    @DrawableRes
    fun getFolderIcon(): Int

    @DrawableRes
    fun getDefaultFileIcon(): Int

    @DrawableRes
    fun getIconForFile(file: File): Int

    @DrawableRes
    fun getIconForExtension(extension: String): Int
    
}