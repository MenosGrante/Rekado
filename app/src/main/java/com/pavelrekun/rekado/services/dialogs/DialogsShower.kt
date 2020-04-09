package com.pavelrekun.rekado.services.dialogs

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Schema
import com.pavelrekun.rekado.databinding.DialogDonateBinding
import com.pavelrekun.rekado.databinding.DialogPayloadDownloadBinding
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsViewModel
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.extensions.isEmpty
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.PreferencesUtils
import com.pavelrekun.rekado.services.utils.Utils
import org.greenrobot.eventbus.EventBus

object DialogsShower {

    fun showPayloadsDialog(activity: BaseActivity): AlertDialog {
        val adapter = ArrayAdapter(activity, R.layout.item_dialog_payload, PayloadHelper.getTitles())
        val builder = MaterialAlertDialogBuilder(activity)

        builder.setTitle(R.string.dialog_loader_title)
        builder.setAdapter(adapter) { dialog, which ->
            val selectedPayload = adapter.getItem(which) as String
            PreferencesUtils.putChosen(PayloadHelper.find(selectedPayload))
            EventBus.getDefault().post(Events.PayloadSelected())
            dialog.cancel()

        }

        builder.setOnDismissListener {
            EventBus.getDefault().post(Events.PayloadNotSelected())
        }

        return builder.create().apply { show() }
    }

    fun showPayloadsResetDialog(activity: BaseActivity): AlertDialog {
        val builder = MaterialAlertDialogBuilder(activity)

        builder.setTitle(R.string.dialog_reset_payloads_title)
        builder.setMessage(R.string.dialog_reset_payloads_summary)

        builder.setNegativeButton(R.string.dialog_button_negative) { _, _ -> }
        builder.setPositiveButton(R.string.dialog_button_positive) { _, _ -> }

        return builder.create().apply { show() }
    }

    fun showPayloadsDownloadDialog(activity: BaseActivity, viewModel: PayloadsViewModel) {
        val binding = DialogPayloadDownloadBinding.inflate(activity.layoutInflater)

        val builder = MaterialAlertDialogBuilder(activity)
        builder.setTitle(R.string.dialog_payload_download_title)

        builder.create().apply {
            setView(binding.root)
            show()

            binding.dialogDownloadPayloadDownload.setOnClickListener {
                if (!binding.dialogDownloadPayloadName.isEmpty() && !binding.dialogDownloadPayloadURL.isEmpty()) {
                    viewModel.downloadPayload(binding.dialogDownloadPayloadName.getString(), binding.dialogDownloadPayloadURL.getString())
                    this.dismiss()
                } else {
                    Toast.makeText(activity, R.string.payloads_download_status_empty, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun showPayloadsUpdatesDialog(activity: BaseActivity, updatedSchema: Schema, viewModel: PayloadsViewModel) {
        val builder = MaterialAlertDialogBuilder(activity)

        builder.setTitle(R.string.dialog_payload_update_title)
        builder.setMessage(R.string.dialog_payload_update_message)

        builder.setNegativeButton(R.string.dialog_button_negative) { _, _ -> }
        builder.setPositiveButton(R.string.dialog_button_update) { _, _ ->
            viewModel.updatePayloads(updatedSchema)
        }

        builder.create().apply { show() }
    }

    fun showDonateDialog(activity: BaseActivity) {
        val binding = DialogDonateBinding.inflate(activity.layoutInflater)
        val builder = MaterialAlertDialogBuilder(activity)

        binding.donateBuyMeCoffee.setOnClickListener { Utils.openLink(activity, Constants.DONATE_LINK) }

        builder.setTitle(R.string.navigation_donate)

        builder.create().apply {
            setView(binding.root)
            show()
        }
    }

}