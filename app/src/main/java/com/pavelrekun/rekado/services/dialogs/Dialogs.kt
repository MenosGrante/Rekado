package com.pavelrekun.rekado.services.dialogs

import android.support.v7.app.AlertDialog
import com.afollestad.materialdialogs.MaterialDialog
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus


object Dialogs {

    fun showPayloadsDialog(activity: BaseActivity) {

        val dialog = MaterialDialog.Builder(activity)
                .title(R.string.dialog_loader_title)
                .backgroundColorRes(R.color.colorPrimary)
                .contentColorAttr(android.R.attr.textColorSecondary)
                .titleColorRes(R.color.colorAccent)
                .items(PayloadHelper.getNames())
                .itemsCallback { dialog, _, _, name ->
                    PayloadHelper.putChosen(PayloadHelper.find(name.toString()) as Payload)
                    dialog.hide()
                }
                .dismissListener {
                    EventBus.getDefault().postSticky(Events.PayloadNotSelected())
                }
                .build()

        dialog.show()
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

}