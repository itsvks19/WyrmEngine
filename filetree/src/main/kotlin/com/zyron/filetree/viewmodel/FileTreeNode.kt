/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.zyron.filetree.viewmodel

import java.io.File

data class FileTreeNode(val file: File, var parent: FileTreeNode? = null, var level: Int = 0) {
    var isExpanded: Boolean = false
    var childrenStartIndex: Int = 0
    var childrenEndIndex: Int = 0
    var childrenLoaded: Boolean = false
}