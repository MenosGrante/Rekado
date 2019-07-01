package com.pavelrekun.rekado.screens.settings_activity.main_settings_fragment

import android.os.Bundle
import android.view.View
import androidx.preference.PreferenceFragmentCompat
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class MainSettingsFragment : PreferenceFragmentCompat() {

    private lateinit var mvpView: MainSettingsContract.View
    private lateinit var activity: BaseActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.setTitle(R.string.navigation_settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        activity = getActivity() as BaseActivity
        addPreferencesFromResource(R.xml.preferences)

        mvpView = MainSettingsView(activity, this)
    }
}