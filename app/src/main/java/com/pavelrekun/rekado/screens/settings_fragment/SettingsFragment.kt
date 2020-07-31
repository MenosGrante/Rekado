package com.pavelrekun.rekado.screens.settings_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.Preference
import com.pavelrekun.penza.Penza
import com.pavelrekun.penza.services.helpers.SettingsDialogsHelper
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BasePreferencesFragment
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.extensions.openSettingsAppearanceThemesScreen
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.LoginUtils
import com.pavelrekun.rekado.services.utils.PreferencesUtils
import com.pavelrekun.rekado.services.utils.Utils
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge

class SettingsFragment : BasePreferencesFragment(R.xml.preferences, R.string.navigation_settings) {

    private lateinit var appearanceTheme: Preference
    private lateinit var appearanceAccentColor: Preference
    private lateinit var appearanceRandomize: Preference
    private lateinit var appearanceReset: Preference

    private lateinit var autoInjectorEnable: CheckBoxPreference
    private lateinit var autoInjectorPayload: ListPreference

    private lateinit var payloadsHidePreference: CheckBoxPreference
    private lateinit var payloadsResetPreference: Preference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePreferences()

        initAppearanceCategory()
        initAutoInjectorCategory()
        initPayloadsCategory()
        initEdgeToEdge()
    }

    private fun preparePreferences() {
        payloadsHidePreference = findPreference("payloads_hide")
        payloadsResetPreference = findPreference("payloads_reset")

        autoInjectorEnable = findPreference("auto_injector_enable")
        autoInjectorPayload = findPreference("auto_injector_payload")

        appearanceTheme = findPreference("appearance_theme")
        appearanceAccentColor = findPreference("appearance_accent_color")
        appearanceRandomize = findPreference("appearance_randomize")
        appearanceReset = findPreference("appearance_reset")
    }

    private fun initAppearanceCategory() {
        appearanceTheme.setOnPreferenceClickListener {
            requireBaseActivity().openSettingsAppearanceThemesScreen()
            true
        }

        appearanceAccentColor.setOnPreferenceChangeListener { _, _ ->
            DialogsShower.showSettingsRestartDialog(requireBaseActivity())
            true
        }

        appearanceRandomize.setOnPreferenceClickListener {
            SettingsDialogsHelper.showSettingsRestartDialog(requireBaseActivity()) {
                Penza.randomizeTheme()
                Utils.restartApplication(requireBaseActivity())
            }
            true
        }

        appearanceReset.setOnPreferenceClickListener {
            SettingsDialogsHelper.showSettingsRestartDialog(requireBaseActivity()) {
                Penza.reset()
                Utils.restartApplication(requireBaseActivity())
            }
            true
        }
    }

    private fun initAutoInjectorCategory() {
        if (!PayloadHelper.checkPayloadsExists()) {
            autoInjectorEnable.isEnabled = false
            autoInjectorEnable.isChecked = false

            autoInjectorPayload.isEnabled = false
            autoInjectorPayload.value = null
        } else {
            autoInjectorEnable.isEnabled = true
            autoInjectorPayload.isEnabled = true

            autoInjectorEnable.setTitle(if (autoInjectorEnable.isChecked) R.string.settings_auto_injector_status_title_enabled else R.string.settings_auto_injector_status_title_disabled)

            autoInjectorPayload.entryValues = PayloadHelper.getTitles().toTypedArray()
            autoInjectorPayload.entries = PayloadHelper.getTitles().toTypedArray()
            if (autoInjectorPayload.value == null) autoInjectorPayload.setValueIndex(0)
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
    }

    private fun initPayloadsCategory() {
        payloadsHidePreference.setOnPreferenceChangeListener { preference, newValue ->
            PreferencesUtils.setHideBundledPayloadsEnabled(newValue as Boolean)
            initAutoInjectorCategory()
            true
        }

        payloadsResetPreference.setOnPreferenceClickListener {
            val dialog = DialogsShower.showPayloadsResetDialog(requireBaseActivity())

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                PayloadHelper.deletePayloads()
                dialog.dismiss()

                LoginUtils.info("Payloads database cleaned!")
            }
            true
        }
    }


    private fun initEdgeToEdge() {
        edgeToEdge {
            listView.fit { Edge.Bottom }
        }
    }

}