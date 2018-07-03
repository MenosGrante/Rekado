package com.pavelrekun.rekado.services.dialogs

import com.afollestad.materialdialogs.MaterialDialog
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus


object Dialogs {

    fun showPayloadsDialog(activity: BaseActivity) {
        PayloadHelper.removeChosenPayload()

        val dialog = MaterialDialog.Builder(activity)
                .title(R.string.loader_dialog_title)
                .backgroundColorRes(R.color.colorPrimary)
                .contentColorAttr(android.R.attr.textColorSecondaryInverse)
                .titleColorRes(R.color.colorAccent)
                .items(PayloadHelper.getPayloadTitles())
                .itemsCallback { _, _, _, name ->
                    PayloadHelper.putChosenPayload(PayloadHelper.findPayload(name.toString()) as Payload)
                }
                .dismissListener {
                    EventBus.getDefault().postSticky(Events.PayloadNotSelected())
                }
                .build()

        dialog.show()
    }

}