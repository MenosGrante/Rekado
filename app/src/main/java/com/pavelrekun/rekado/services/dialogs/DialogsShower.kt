package com.pavelrekun.rekado.services.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.databinding.DialogDonateBinding
import com.pavelrekun.rekado.databinding.DialogPayloadDownloadBinding
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsViewModel
import com.pavelrekun.rekado.services.constants.Links
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.extensions.isEmpty
import com.pavelrekun.rekado.services.handlers.PayloadsHandler
import com.pavelrekun.rekado.services.utils.Utils

object DialogsShower {

    inline fun showSettingsDesignDarkModeDialog(
            context: Context,
            currentIndex: Int,
            crossinline selectedListener: (Int) -> Unit
    ) {
        val data = context.resources.getStringArray(R.array.settings_appearance_theme)
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_settings_appearance_theme_title)

        builder.setSingleChoiceItems(data, currentIndex) { dialog, which ->
            selectedListener.invoke(which)
            dialog.dismiss()
        }

        builder.setNegativeButton(R.string.dialog_button_negative_cancel) { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    inline fun showSettingsAutoInjectorPayloadDialog(
            context: Context,
            payloads: MutableList<Payload>,
            currentPayload: String,
            crossinline selectedListener: (String) -> Unit
    ) {
        val currentIndex = payloads.indexOfFirst { it.title == currentPayload }
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_settings_auto_injector_payload_title)
        builder.setSingleChoiceItems(payloads.map { it.title }.toTypedArray(), currentIndex) { dialog, which ->
            selectedListener.invoke(payloads[which].title)
            dialog.dismiss()
        }

        builder.setNegativeButton(R.string.dialog_button_negative_cancel) { dialog, _ ->
            dialog.dismiss()
        }

        builder.create().show()
    }

    inline fun showPayloadsDialog(context: Context,
                                  payloadsHandler: PayloadsHandler,
                                  crossinline injectionListener: (Payload) -> Unit,
                                  crossinline dismissListener: () -> Unit): AlertDialog {
        val adapter = ArrayAdapter(context, R.layout.item_dialog_payload, payloadsHandler.getTitles())
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_loader_title)
        builder.setAdapter(adapter) { dialog, which ->
            val selectedPayload = adapter.getItem(which) as String
            injectionListener.invoke(payloadsHandler.find(selectedPayload))
            dialog.cancel()
        }

        builder.setOnDismissListener {
            dismissListener.invoke()
        }

        return builder.create().apply { show() }
    }

    inline fun showNoPayloadsDialog(context: Context, crossinline dismissListener: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_loader_no_payloads_title)
        builder.setMessage(R.string.dialog_loader_no_payloads_description)

        builder.setNegativeButton(R.string.dialog_button_negative_close) { _, _ ->
            dismissListener.invoke()
        }

        builder.create().apply { show() }
    }

    fun showPayloadsResetDialog(context: Context): AlertDialog {
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_reset_payloads_title)
        builder.setMessage(R.string.dialog_reset_payloads_summary)

        builder.setNegativeButton(R.string.dialog_button_negative_close) { _, _ -> }
        builder.setPositiveButton(R.string.dialog_button_positive) { _, _ -> }

        return builder.create().apply { show() }
    }

    fun showPayloadsDownloadDialog(context: Context, viewModel: PayloadsViewModel) {
        val binding = DialogPayloadDownloadBinding.inflate(LayoutInflater.from(context), null, false)

        val builder = MaterialAlertDialogBuilder(context)
        builder.setTitle(R.string.dialog_payload_download_title)

        builder.create().apply {
            setView(binding.root)
            show()

            binding.dialogDownloadPayloadDownload.setOnClickListener {
                if (!binding.dialogDownloadPayloadTitle.isEmpty() && !binding.dialogDownloadPayloadURL.isEmpty()) {
                    viewModel.downloadPayload(binding.dialogDownloadPayloadTitle.getString(), binding.dialogDownloadPayloadURL.getString())
                    this.dismiss()
                } else {
                    Toast.makeText(context, R.string.payloads_download_status_empty, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    inline fun showPayloadsUpdatesDialog(context: Context, crossinline updateListener: () -> Unit) {
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_payload_update_title)
        builder.setMessage(R.string.dialog_payload_update_message)

        builder.setNegativeButton(R.string.dialog_button_negative_close) { _, _ -> }
        builder.setPositiveButton(R.string.dialog_button_update) { _, _ ->
            updateListener.invoke()
        }

        builder.create().apply { show() }
    }

    fun showPayloadsNoUpdatesDialog(context: Context) {
        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_payload_no_updates_title)
        builder.setMessage(R.string.dialog_payload_no_updates_message)

        builder.setPositiveButton(R.string.dialog_button_positive) { _, _ -> }

        builder.create().apply { show() }
    }

    fun showPayloadsNetworkErrorDialog(context: Context, message: String? = null) {
        val dialogMessage = if (message != null) {
            context.getString(R.string.dialog_payload_network_error_message) + "\n\n" +
                    context.getString(R.string.dialog_payload_network_error_message_placeholder, message)
        } else {
            context.getString(R.string.dialog_payload_network_error_message)
        }

        val builder = MaterialAlertDialogBuilder(context)

        builder.setTitle(R.string.dialog_payload_network_error_title)
        builder.setMessage(dialogMessage)

        builder.setNegativeButton(R.string.dialog_button_negative_close) { _, _ -> }

        builder.create().apply { show() }
    }

    fun showDonateDialog(context: Context) {
        val binding = DialogDonateBinding.inflate(LayoutInflater.from(context), null, false)
        val builder = MaterialAlertDialogBuilder(context)

        binding.donateButtonPayPal.setOnClickListener { Utils.openLink(context, Links.DONATION_PAY_PAL) }

        builder.setTitle(R.string.navigation_donate)

        builder.create().apply {
            setView(binding.root)
            show()
        }
    }

}