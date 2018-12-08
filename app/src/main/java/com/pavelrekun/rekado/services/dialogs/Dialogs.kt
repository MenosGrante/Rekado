package com.pavelrekun.rekado.services.dialogs

import android.content.Intent
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.callbacks.onDismiss
import com.afollestad.materialdialogs.list.listItems
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.screens.main_activity.MainActivity
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus


object Dialogs {

    fun showDialog(activity: BaseActivity, @StringRes title: Int, @StringRes description: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(title)
        builder.setMessage(description)

        val dialog = builder.create()
        dialog.show()
    }

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

    fun showRestartDialog(activity: BaseActivity) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.settings_restart_title)
        builder.setMessage(R.string.settings_restart_message)

        builder.setNegativeButton(R.string.settings_restart_cancel) { _, _ -> }

        builder.setPositiveButton(R.string.settings_restart_ok) { _, _ ->
            ActivityCompat.finishAffinity(activity)
            activity.startActivity(Intent(activity, MainActivity::class.java))
        }

        val restartDialog = builder.create()
        restartDialog.show()
    }

}