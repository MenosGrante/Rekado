package com.pavelrekun.rekado.services.dialogs

import android.content.Intent
import android.support.annotation.StringRes
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.widget.TextView
import com.afollestad.materialdialogs.MaterialDialog
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

        val dialog = MaterialDialog.Builder(activity)
                .title(R.string.dialog_loader_title)
                .backgroundColorRes(R.color.colorPrimary)
                .contentColorAttr(android.R.attr.textColorPrimary)
                .titleColorAttr(android.R.attr.textColorPrimary)
                .items(PayloadHelper.getNames())
                .itemsCallback { dialog, _, _, name ->
                    PayloadHelper.putChosen(PayloadHelper.find(name.toString()) as Payload)
                    EventBus.getDefault().post(Events.PayloadSelected())
                    dialog.hide()
                }

                .dismissListener {
                    EventBus.getDefault().post(Events.PayloadNotSelected())
                }

                .build()

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