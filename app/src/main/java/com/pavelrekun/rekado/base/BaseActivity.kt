package com.pavelrekun.rekado.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.screens.main_activity.MainActivity
import com.pavelrekun.penza.base.PenzaActivity
import com.pavelrekun.penza.services.helpers.ColorsHelper
import com.pavelrekun.penza.services.helpers.DesignHelper

@SuppressLint("Registered")
open class BaseActivity : PenzaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDesignRules()
    }

    override fun setTitle(titleResId: Int) {
        supportActionBar?.title = getString(titleResId)
    }

    private fun initDesignRules() {
        DesignHelper.tintTaskDescription(this, R.mipmap.ic_launcher, R.string.app_name, ColorsHelper.resolveColorAttribute(this, android.R.attr.windowBackground))

        if (this is MainActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                DesignHelper.tintNavigationBar(this, ColorsHelper.resolveColorAttribute(this, R.attr.colorBackgroundSecondary))
            }
        }
    }
}