package com.pavelrekun.rekado.base

import android.os.Bundle
import com.pavelrekun.rekado.services.utils.PreferencesUtils
import com.pavelrekun.siga.base.SigaActivity

open class BaseActivity : SigaActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        PreferencesUtils.getPreferencesTheme()
        super.onCreate(savedInstanceState)
    }

    override fun setTitle(titleResId: Int) {
        supportActionBar?.title = getString(titleResId)
    }

}