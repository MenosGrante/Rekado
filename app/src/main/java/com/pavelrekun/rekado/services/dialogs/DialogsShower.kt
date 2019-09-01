package com.pavelrekun.rekado.services.dialogs

import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.extensions.isEmpty
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.Utils
import kotlinx.android.synthetic.main.dialog_donate.view.*
import kotlinx.android.synthetic.main.dialog_payload_download.*
import org.greenrobot.eventbus.EventBus

object DialogsShower {

    fun showPayloadsDialog(activity: BaseActivity): AlertDialog {
        val adapter = ArrayAdapter<String>(activity, R.layout.item_dialog_payload, PayloadHelper.getNames())
        val builder = MaterialAlertDialogBuilder(activity)

        builder.setTitle(R.string.dialog_loader_title)
        builder.setAdapter(adapter) { dialog, which ->
            val selectedPayload = adapter.getItem(which) as String
            PayloadHelper.putChosen(PayloadHelper.find(selectedPayload))
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

    fun showPayloadsDownloadDialog(activity: BaseActivity) {
        val view = activity.layoutInflater.inflate(R.layout.dialog_payload_download, null)

        val builder = MaterialAlertDialogBuilder(activity)
        builder.setTitle(R.string.dialog_payload_download_title)

        builder.create().apply {
            setView(view)
            show()

            dialogDownloadPayloadDownload.setOnClickListener {
                if (!this.dialogDownloadPayloadName.isEmpty() && !this.dialogDownloadPayloadURL.isEmpty()) {
                    PayloadHelper.downloadPayload(activity, this.dialogDownloadPayloadName.getString(), this.dialogDownloadPayloadURL.getString())
                    this.dismiss()
                } else {
                    Toast.makeText(activity, R.string.payloads_download_status_empty, Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    fun showDonateDialog(activity: BaseActivity) {
        val view = activity.layoutInflater.inflate(R.layout.dialog_donate, null)
        val builder = MaterialAlertDialogBuilder(activity)

        view.donateBuyMeCoffee.setOnClickListener { Utils.openLink(activity, Constants.DONATE_LINK) }

        builder.setTitle(R.string.navigation_donate)

        builder.create().apply {
            setView(view)
            show()
        }
    }

}