package com.pavelrekun.rekado.screens.settings_activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.SettingsUtils

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)

        initPayloadsCategoryPreferences()
    }

    private fun initPayloadsCategoryPreferences() {
        val payloadsResetPreference = findPreference("payloads_reset")

        val autoInjectorEnable = findPreference("auto_injector_enable") as CheckBoxPreference
        val autoInjectorPayload = findPreference("auto_injector_payload") as ListPreference

        autoInjectorEnable.setTitle(if (autoInjectorEnable.isChecked) R.string.settings_auto_injector_status_title_enabled else R.string.settings_auto_injector_status_title_disabled)

        autoInjectorPayload.entryValues = PayloadHelper.getNames().toTypedArray()
        autoInjectorPayload.entries = PayloadHelper.getNames().toTypedArray()
        if (autoInjectorPayload.value == null) autoInjectorPayload.setValueIndex(0)
        autoInjectorPayload.isEnabled = autoInjectorEnable.isChecked

        autoInjectorEnable.setOnPreferenceChangeListener { _, newValue ->
            autoInjectorPayload.isEnabled = newValue as Boolean

            if (newValue) {
                LogHelper.log(1, "\"Auto injector\" enabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_enabled)
            } else {
                LogHelper.log(1, "\"Auto injector\" disabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_disabled)
            }

            SettingsUtils.updateAutoInjectorEnabled(newValue)
            true
        }

        autoInjectorPayload.setOnPreferenceChangeListener { _, newValue ->
            SettingsUtils.updateAutoInjectorPayload(newValue.toString())
            true
        }

        payloadsResetPreference.setOnPreferenceClickListener {
            val dialog = Dialogs.showPayloadsResetDialog(activity as BaseActivity)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                PayloadHelper.clearFolder()
                dialog.dismiss()

                LogHelper.log(1, "Payloads database cleaned!")
            }
            true
        }
    }
}