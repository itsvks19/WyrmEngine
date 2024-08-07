/*
 * This file is a part of WyrmEngine.
 *
 * This software is provided 'as-is', without any express or implied warranty.
 * In no event will the authors be held liable for any damages arising from the use of this software.
 *
 * For terms of use and licensing, please see the End-User License Agreement (EULA).
 */

package com.wyrm.engine.activities

import android.os.Bundle
import com.wyrm.engine.activities.base.BaseActivity
import com.wyrm.engine.databinding.ActivityCodeBinding

class CodeActivity : BaseActivity<ActivityCodeBinding>(ActivityCodeBinding::inflate) {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
  }
}