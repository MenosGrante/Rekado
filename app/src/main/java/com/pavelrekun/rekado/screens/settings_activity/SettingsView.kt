package com.pavelrekun.rekado.screens.settings_activity

import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.settings_activity.main_settings_fragment.MainSettingsFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsView(private val activity: BaseActivity) : SettingsContract.View {

    init {
        initViews()
    }

    override fun initViews() {
        initToolbar()
        initFragment()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.settingsToolbar)
        activity.settingsToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    override fun initFragment() {
        activity.supportFragmentManager.apply { beginTransaction().replace(R.id.settingsFragmentFrame, MainSettingsFragment()).commit() }
    }
}