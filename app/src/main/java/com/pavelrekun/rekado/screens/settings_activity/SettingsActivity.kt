package com.pavelrekun.rekado.screens.settings_activity

import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class SettingsActivity : BaseActivity() {

    private lateinit var mvpView: SettingsContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        mvpView = SettingsView(this)
    }
}