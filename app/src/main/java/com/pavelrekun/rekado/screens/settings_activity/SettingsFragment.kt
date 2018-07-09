package com.pavelrekun.rekado.screens.settings_activity

import android.app.AlertDialog
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.payloads.PayloadHelper

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.settings)

        initPayloadsCategoryPreferences()
    }

    private fun initPayloadsCategoryPreferences() {
        val payloadsResetPreference = findPreference("payloads_reset")

        payloadsResetPreference.setOnPreferenceClickListener {
            val dialog = Dialogs.showPayloadsResetDialog(activity as BaseActivity)

            dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
                PayloadHelper.deletePayloads()
                dialog.dismiss()
            }
            true
        }
    }
}