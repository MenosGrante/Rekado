package com.pavelrekun.rekado.base

import android.os.Bundle
import com.pavelrekun.rang.activity.RangActivity
import com.pavelrekun.rekado.services.utils.DesignUtils

open class BaseActivity : RangActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        DesignUtils.setNightTheme()
        DesignUtils.applyColorToTaskDescription(this)
        super.onCreate(savedInstanceState)
    }

    override fun setTitle(titleResId: Int) {
        supportActionBar?.title = getString(titleResId)
    }

}