package com.pavelrekun.rekado.services.dialogs

import androidx.appcompat.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItems
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import kotlinx.android.synthetic.main.dialog_payload_download.*
import org.greenrobot.eventbus.EventBus


object Dialogs {

    fun showPayloadsDialog(activity: BaseActivity): MaterialDialog {
        val dialog = MaterialDialog(activity)
                .title(R.string.dialog_loader_title)
                .listItems(items = PayloadHelper.getNames()) { dialog, index, text ->
                    PayloadHelper.putChosen(PayloadHelper.find(text) as Payload)
                    EventBus.getDefault().post(Events.PayloadSelected())
                    dialog.hide()
                }
                .onDismiss { EventBus.getDefault().post(Events.PayloadNotSelected()) }

        dialog.show()

        return dialog
    }

    fun showPayloadsResetDialog(activity: BaseActivity): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.dialog_reset_payloads_title)
        builder.setMessage(R.string.dialog_reset_payloads_summary)

        builder.setNegativeButton(R.string.dialog_negative) { _, _ -> }
        builder.setPositiveButton(R.string.dialog_positive) { _, _ -> }

        val resetDialog = builder.create()
        resetDialog.show()

        return resetDialog
    }

    fun showPayloadsDownloadDialog(activity: BaseActivity) {
        val view = activity.layoutInflater.inflate(R.layout.dialog_payload_download, null)

        val builder = AlertDialog.Builder(activity)
        builder.setView(view)
        builder.setTitle(R.string.dialog_payload_download_title)

        val dialog = builder.create()
        dialog.show()

        dialog.dialogDownloadPayloadDownload.setOnClickListener {
            PayloadHelper.downloadPayload(activity, dialog.dialogDownloadPayloadName.getString(), dialog.dialogDownloadPayloadURL.getString())
            dialog.dismiss()
        }
    }

}