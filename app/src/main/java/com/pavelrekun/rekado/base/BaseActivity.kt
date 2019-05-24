package com.pavelrekun.rekado.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.screens.main_activity.MainActivity
import com.pavelrekun.rekado.services.utils.PreferencesUtils
import com.pavelrekun.siga.base.SigaActivity
import com.pavelrekun.siga.services.helpers.ColorsHelper
import com.pavelrekun.siga.services.helpers.DesignHelper

@SuppressLint("Registered")
open class BaseActivity : SigaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesUtils.getPreferencesTheme()
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