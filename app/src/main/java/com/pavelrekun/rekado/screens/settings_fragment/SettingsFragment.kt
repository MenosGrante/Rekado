package com.pavelrekun.rekado.screens.settings_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.preference.CheckBoxPreference
import androidx.preference.Preference
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BasePreferencesFragment
import com.pavelrekun.rekado.data.base.Theme
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.extensions.restartApp
import com.pavelrekun.rekado.services.handlers.PayloadsHandler
import com.pavelrekun.rekado.services.handlers.PreferencesHandler
import com.pavelrekun.rekado.services.utils.LoginUtils
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BasePreferencesFragment(R.xml.preferences, R.string.navigation_settings) {

    @Inject
    lateinit var payloadsHandler: PayloadsHandler

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

    private lateinit var appearanceTheme: Preference
    private lateinit var appearanceDynamicColors: Preference

    private lateinit var autoInjectorEnable: CheckBoxPreference
    private lateinit var autoInjectorPayload: Preference

    private lateinit var payloadsHidePreference: CheckBoxPreference
    private lateinit var payloadsResetPreference: Preference

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePreferences()

        initWithTitle(R.string.navigation_settings)
        initAppearanceCategory()
        initAutoInjectorCategory()
        initPayloadsCategory()
        initEdgeToEdge()
        initStrings()
    }

    private fun preparePreferences() {
        payloadsHidePreference = findPreference("payloads_hide")
        payloadsResetPreference = findPreference("payloads_reset")

        autoInjectorEnable = findPreference("auto_injector_enable")
        autoInjectorPayload = findPreference("auto_injector_payload")

        appearanceTheme = findPreference("appearance_theme")
        appearanceDynamicColors = findPreference("appearance_dynamic_colors")
    }

    private fun initAppearanceCategory() {
        appearanceTheme.setOnPreferenceClickListener {
            DialogsShower.showSettingsDesignDarkModeDialog(
                requireContext(),
                preferencesHandler.checkAppearanceThemeMode().ordinal
            ) {
                preferencesHandler.saveAppearanceThemeMode(Theme.find(it))
                requireActivity().restartApp()
            }
            true
        }

        appearanceDynamicColors.setOnPreferenceChangeListener { _, _ ->
            requireActivity().recreate()
            true
        }
    }

    private fun initAutoInjectorCategory() {
        if (!payloadsHandler.checkPayloadsExists()) {
            autoInjectorEnable.isEnabled = false
            autoInjectorEnable.isChecked = false

            autoInjectorPayload.isEnabled = false
        } else {
            autoInjectorEnable.isEnabled = true
            autoInjectorPayload.isEnabled = true

            autoInjectorPayload.summary = preferencesHandler.getAutoInjectorPayload(payloadsHandler.getAllPayloads().first().title)
            autoInjectorPayload.setOnPreferenceClickListener {
                DialogsShower.showSettingsAutoInjectorPayloadDialog(requireContext(),
                        payloadsHandler.getAllPayloads(),
                        preferencesHandler.getAutoInjectorPayload(payloadsHandler.getAllPayloads().first().title)) {
                        preferencesHandler.saveAutoInjectorPayload(it)
                    autoInjectorPayload.summary = it
                }

                true
            }
            autoInjectorPayload.isEnabled = autoInjectorEnable.isChecked

            autoInjectorEnable.setOnPreferenceChangeListener { _, newValue ->
                autoInjectorPayload.isEnabled = newValue as Boolean

                preferencesHandler.clearAutoInjectorPayload()
                autoInjectorPayload.summary = preferencesHandler.getAutoInjectorPayload(payloadsHandler.getAllPayloads().first().title)

                if (newValue) {
                    LoginUtils.info("\"Auto injector\" enabled!")
                } else {
                    LoginUtils.info("\"Auto injector\" disabled!")
                }

                true
            }
        }
    }

    private fun initPayloadsCategory() {
        payloadsHidePreference.setOnPreferenceChangeListener { _, newValue ->
            preferencesHandler.setHideBundledPayloadsEnabled(newValue as Boolean)
            initAutoInjectorCategory()
            true
        }

        payloadsResetPreference.setOnPreferenceClickListener {
            val dialog = DialogsShower.showPayloadsResetDialog(requireContext())

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                payloadsHandler.deletePayloads()
                dialog.dismiss()

                LoginUtils.info("Payloads database cleaned!")
            }
            true
        }
    }

    private fun initStrings() {
        payloadsHidePreference.summary = getString(R.string.settings_payloads_hide_summary, getString(R.string.helper_bundled_payloads))
        payloadsResetPreference.summary = getString(R.string.settings_payloads_reset_summary, getString(R.string.helper_bundled_payloads))
    }

    private fun initEdgeToEdge() {
        listView.applyInsetter {
            type(navigationBars = true) {
                padding()
            }
        }
    }

}