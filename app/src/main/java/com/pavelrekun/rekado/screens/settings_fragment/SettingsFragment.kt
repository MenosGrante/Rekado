package com.pavelrekun.rekado.screens.settings_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.pavelrekun.penza.Penza
import com.pavelrekun.penza.pickers.theme.ThemePickerFragment
import com.pavelrekun.penza.services.helpers.SettingsDialogsHelper
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.utils.LoginUtils
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.Utils

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var activity: BaseActivity

    private lateinit var appearanceTheme: Preference
    private lateinit var appearanceAccentColor: Preference
    private lateinit var appearanceRandomize: Preference
    private lateinit var appearanceReset: Preference

    private lateinit var autoInjectorEnable: CheckBoxPreference
    private lateinit var autoInjectorPayload: ListPreference

    private lateinit var payloadsResetPreference: Preference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.setTitle(R.string.navigation_settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        activity = getActivity() as BaseActivity
        addPreferencesFromResource(R.xml.preferences)

        preparePreferences()

        initAppearanceCategory()
        initAutoInjectorCategory()
        initPayloadsCategory()
    }

    private fun preparePreferences() {
        payloadsResetPreference = findPreference("payloads_reset")!!

        autoInjectorEnable = findPreference("auto_injector_enable")!!
        autoInjectorPayload = findPreference("auto_injector_payload")!!

        appearanceTheme = findPreference("appearance_theme")!!
        appearanceAccentColor = findPreference("appearance_accent_color")!!
        appearanceRandomize = findPreference("appearance_randomize")!!
        appearanceReset = findPreference("appearance_reset")!!
    }

    private fun initAppearanceCategory() {
        val themePickerFragment = ThemePickerFragment().apply {
            setClickListener { openRestartDialog() }
            setControlClickListener { openRestartDialog() }
        }

        appearanceTheme.setOnPreferenceClickListener {
            openSettingsFragment(themePickerFragment)
            true
        }

        appearanceAccentColor.setOnPreferenceChangeListener { _, _ ->
            openRestartDialog()
            true
        }

        appearanceRandomize.setOnPreferenceClickListener {
            SettingsDialogsHelper.showSettingsRestartDialog(activity) {
                Penza.randomizeTheme()
                Utils.restartApplication()
            }
            true
        }

        appearanceReset.setOnPreferenceClickListener {
            SettingsDialogsHelper.showSettingsRestartDialog(activity) {
                Penza.reset()
                Utils.restartApplication()
            }
            true
        }
    }

    private fun initAutoInjectorCategory() {
        autoInjectorEnable.setTitle(if (autoInjectorEnable.isChecked) R.string.settings_auto_injector_status_title_enabled else R.string.settings_auto_injector_status_title_disabled)

        autoInjectorPayload.entryValues = PayloadHelper.getTitles().toTypedArray()
        autoInjectorPayload.entries = PayloadHelper.getTitles().toTypedArray()
        if (autoInjectorPayload.value == null && PayloadHelper.getTitles().isNotEmpty()) autoInjectorPayload.setValueIndex(0)
        autoInjectorPayload.isEnabled = autoInjectorEnable.isChecked

        autoInjectorEnable.setOnPreferenceChangeListener { _, newValue ->
            autoInjectorPayload.isEnabled = newValue as Boolean

            if (newValue) {
                LoginUtils.info("\"Auto injector\" enabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_enabled)
            } else {
                LoginUtils.info("\"Auto injector\" disabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_disabled)
            }

            true
        }
    }

    private fun initPayloadsCategory() {
        payloadsResetPreference.setOnPreferenceClickListener {
            val dialog = DialogsShower.showPayloadsResetDialog(activity)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                PayloadHelper.deletePayloads()
                dialog.dismiss()

                LoginUtils.info("Payloads database cleaned!")
            }
            true
        }
    }

    private fun openSettingsFragment(fragment: Fragment) {
        activity.supportFragmentManager.apply {
            beginTransaction()
                    .replace(R.id.secondaryContainer, fragment, fragment::class.java.simpleName)
                    .addToBackStack(null)
                    .commit()
        }
    }

    private fun openRestartDialog() {
        SettingsDialogsHelper.showSettingsRestartDialog(activity) {
            Utils.restartApplication()
        }
    }

}