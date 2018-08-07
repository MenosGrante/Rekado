package com.pavelrekun.rekado.screens.settings_activity

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.preference.CheckBoxPreference
import android.support.v7.preference.ListPreference
import android.support.v7.preference.PreferenceFragmentCompat
import android.widget.Toast
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import com.pavelrekun.rekado.services.utils.SettingsUtils

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)

        if (!PermissionsUtils.checkPermissionGranted(activity as BaseActivity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity as BaseActivity, this, PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE)
        } else {
            initPayloadsCategoryPreferences()
        }
    }

    private fun initPayloadsCategoryPreferences() {
        val payloadsResetPreference = findPreference("payloads_reset")

        val autoInjectorEnable = findPreference("auto_injector_enable") as CheckBoxPreference
        val autoInjectorPayload = findPreference("auto_injector_payload") as ListPreference

        val appearanceNightMode = findPreference("appearance_night_mode") as ListPreference
        val appearanceAccentColor = findPreference("appearance_accent_color")

        autoInjectorEnable.setTitle(if (autoInjectorEnable.isChecked) R.string.settings_auto_injector_status_title_enabled else R.string.settings_auto_injector_status_title_disabled)

        autoInjectorPayload.entryValues = PayloadHelper.getNames().toTypedArray()
        autoInjectorPayload.entries = PayloadHelper.getNames().toTypedArray()
        if (autoInjectorPayload.value == null) autoInjectorPayload.setValueIndex(0)
        autoInjectorPayload.isEnabled = autoInjectorEnable.isChecked

        autoInjectorEnable.setOnPreferenceChangeListener { _, newValue ->
            autoInjectorPayload.isEnabled = newValue as Boolean

            if (newValue) {
                LogHelper.log(LogHelper.INFO, "\"Auto injector\" enabled!")
                autoInjectorEnable.setTitle(R.string.settings_auto_injector_status_title_enabled)
            } else {
                LogHelper.log(LogHelper.INFO, "\"Auto injector\" disabled!")
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

                LogHelper.log(LogHelper.INFO, "Payloads database cleaned!")
            }
            true
        }

        appearanceNightMode.setOnPreferenceChangeListener { _, _ ->
            Dialogs.showRestartDialog(activity as BaseActivity)
            true
        }

        appearanceAccentColor.setOnPreferenceChangeListener { _, _ ->
            activity?.recreate()
            true
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initPayloadsCategoryPreferences()
            } else {
                Toast.makeText(activity, R.string.permission_storage_error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}