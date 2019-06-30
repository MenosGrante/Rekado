package com.pavelrekun.rekado.screens.settings_activity

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.preference.CheckBoxPreference
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import com.google.android.material.snackbar.Snackbar
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Logger
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import com.pavelrekun.rekado.services.utils.SettingsUtils
import com.pavelrekun.rekado.services.utils.Utils
import com.pavelrekun.siga.pickers.theme.ThemePickerFragment
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsFragment : PreferenceFragmentCompat() {

    private lateinit var activity: BaseActivity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity.setTitle(R.string.navigation_settings)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        activity = getActivity() as BaseActivity

        addPreferencesFromResource(R.xml.preferences)

        initPayloadsCategoryPreferences()
    }

    private fun initPayloadsCategoryPreferences() {
        if (!SettingsUtils.checkHideBundledEnabled()) {
            MemoryUtils.copyBundledPayloads()
        }

        val payloadsHideBundled = findPreference("payloads_hide_bundled")
        val payloadsResetPreference = findPreference("payloads_reset")

        val autoInjectorEnable = findPreference("auto_injector_enable") as CheckBoxPreference
        val autoInjectorPayload = findPreference("auto_injector_payload") as ListPreference

        val appearanceTheme = findPreference("appearance_theme")
        val appearanceAccentColor = findPreference("appearance_accent_color")

        autoInjectorEnable.setTitle(if (autoInjectorEnable.isChecked) R.string.settings_auto_injector_status_title_enabled else R.string.settings_auto_injector_status_title_disabled)

        autoInjectorPayload.entryValues = PayloadHelper.getNames().toTypedArray()
        autoInjectorPayload.entries = PayloadHelper.getNames().toTypedArray()
        if (autoInjectorPayload.value == null && PayloadHelper.getNames().isNotEmpty()) autoInjectorPayload.setValueIndex(0)
        autoInjectorPayload.isEnabled = autoInjectorEnable.isChecked

        autoInjectorEnable.setOnPreferenceChangeListener { _, newValue ->
            autoInjectorPayload.isEnabled = newValue as Boolean

            if (newValue) {
                Logger.info("\"Auto injector\" enabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_enabled)
            } else {
                Logger.info("\"Auto injector\" disabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_disabled)
            }

            SettingsUtils.updateAutoInjectorEnabled(newValue)
            true
        }

        autoInjectorPayload.setOnPreferenceChangeListener { _, newValue ->
            SettingsUtils.updateAutoInjectorPayload(newValue.toString())
            true
        }

        payloadsHideBundled.setOnPreferenceChangeListener { _, newValue ->
            SettingsUtils.updateHideBundledEnabled(newValue as Boolean)
            PayloadHelper.clearBundled()
            true
        }

        payloadsResetPreference.setOnPreferenceClickListener {
            val dialog = Dialogs.showPayloadsResetDialog(activity)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                PayloadHelper.clearFolderWithoutBundled()
                dialog.dismiss()

                Logger.info("Payloads database cleaned!")
            }
            true
        }

        val themePickerFragment = ThemePickerFragment()

        themePickerFragment.setClickListener {
            openUpdatingMessage()
        }

        appearanceTheme.setOnPreferenceClickListener {
            openSettingsFragment(themePickerFragment)

            true
        }

        appearanceAccentColor.setOnPreferenceChangeListener { _, _ ->
            openUpdatingMessage()
            true
        }
    }

    private fun openSettingsFragment(fragment: Fragment) {
        activity.supportFragmentManager?.beginTransaction()?.setCustomAnimations(R.anim.fade_in, R.anim.fade_out)?.replace(R.id.settingsFragmentFrame, fragment, fragment::class.java.simpleName)?.addToBackStack(null)?.commit()
    }

    private fun openUpdatingMessage() {
        val updatingMessage = Snackbar.make(activity.settingsFragmentFrame, R.string.settings_appearance_updating, 500)

        updatingMessage.addCallback(object : Snackbar.Callback() {
            override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                super.onDismissed(transientBottomBar, event)
                Utils.restartActivity(activity)
            }
        }).show()
    }
}