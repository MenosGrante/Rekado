package com.pavelrekun.rekado.services.dialogs

import android.app.Activity
import android.content.Intent
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

    fun showInjectorSelectorDialog(activity: BaseActivity): AlertDialog {
        val builder = AlertDialog.Builder(activity)
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_injector_selector, null)
        builder.setView(view)
        builder.setTitle(R.string.dialog_injector_chooser_title)

        val bootPayload = view.findViewById<TextView>(R.id.dialog_injector_selector_payload)
        val bootLakka = view.findViewById<TextView>(R.id.dialog_injector_selector_lakka)

        val dialog = builder.create()
        dialog.show()

        dialog.setOnDismissListener {
            EventBus.getDefault().post(Events.InjectorMethodNotSelected())
        }

        bootPayload.setOnClickListener {
            EventBus.getDefault().post(Events.InjectorMethodPayloadSelected())
            dialog.hide()
        }

        bootLakka.setOnClickListener {
            EventBus.getDefault().post(Events.InjectorMethodLakkaSelected())
            dialog.hide()
        }

        return dialog
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

    fun openRestartDialog(activity: Activity) {
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