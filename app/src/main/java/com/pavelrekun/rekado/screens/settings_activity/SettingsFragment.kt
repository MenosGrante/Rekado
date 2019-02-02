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
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.logs.LogHelper
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

        addPreferencesFromResource(R.xml.settings)

        if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity, this, PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE)
        } else {
            initPayloadsCategoryPreferences()
        }
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

                LogHelper.log(LogHelper.INFO, "Payloads database cleaned!")
            }
            true
        }

        appearanceTheme.setOnPreferenceClickListener {
            openSettingsFragment(ThemePickerFragment {
                openUpdatingMessage()
            })

            true
        }

        appearanceAccentColor.setOnPreferenceChangeListener { _, _ ->
            activity.recreate()
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