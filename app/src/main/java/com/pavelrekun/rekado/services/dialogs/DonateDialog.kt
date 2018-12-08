package com.pavelrekun.rekado.services.dialogs

import android.app.Dialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.Window
import android.widget.Toast
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.utils.Utils
import kotlinx.android.synthetic.main.dialog_donate.*

class DonateDialog(val activity: BaseActivity) : Dialog(activity) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_donate)
        initClickListeners()
    }

    private fun initClickListeners() {
        donateBuyMeCoffee.setOnClickListener { Utils.openLink(activity, Constants.DONATE_LINK) }
    }
}
