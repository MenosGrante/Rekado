package com.pavelrekun.rekado.screens.settings_activity.main_settings_fragment

import androidx.fragment.app.Fragment

interface MainSettingsContract {

    interface View {

        fun onCreatePreferences()

        fun preparePreferences()

        fun initAppearanceCategory()

        fun initAutoInjectorCategory()

        fun initPayloadsCategory()

        fun openSettingsFragment(fragment: Fragment)

    }

}