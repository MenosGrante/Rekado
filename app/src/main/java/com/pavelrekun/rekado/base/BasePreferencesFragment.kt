package com.pavelrekun.rekado.base

import android.os.Bundle
import android.view.Menu
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.pavelrekun.rekado.screens.navigation_activity.NavigationActivity
import com.pavelrekun.rekado.services.handlers.AppearanceHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
open class BasePreferencesFragment(private val preferencesLayoutId: Int, private val titleResId: Int) : PreferenceFragmentCompat() {

    @Inject
    lateinit var appearanceHandler: AppearanceHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onStart() {
        super.onStart()

        (requireActivity() as NavigationActivity).supportActionBar?.setTitle(titleResId)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(preferencesLayoutId)

        // Dynamic colors are not supported on devices pre-Android 12
        if (!appearanceHandler.checkDynamicColorsAvailable()) {
            findPreference<Preference>("appearance_dynamic_colors").isVisible = false
        }
    }

    override fun <T : Preference> findPreference(key: CharSequence): T {
        return super.findPreference(key)!!
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
    }

    fun initWithTitle(resId: Int) = (requireActivity() as NavigationActivity).setTitle(resId)

}