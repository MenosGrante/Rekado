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
import kotlinx.android.synthetic.main.dialog_donate.*

class DonateDialog(activity: BaseActivity) : Dialog(activity) {

    init {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_donate)
        initClickListeners()
    }

    private fun initClickListeners() {
        donateBitcoin.setOnClickListener { copyInClipBoard("Bitcoin", Constants.BITCOIN_ADDRESS) }
        donateBitcoinCash.setOnClickListener { copyInClipBoard("Bitcoin Cash", Constants.BITCOIN_CASH_ADDRESS) }
        donateEthereum.setOnClickListener { copyInClipBoard("Ethereum", Constants.ETHEREUM_ADDRESS) }
        donateLitecoin.setOnClickListener { copyInClipBoard("Litecoin", Constants.LITECOIN_ADDRESS) }
    }

    private fun copyInClipBoard(type: String, address: String) {
        val clipboard = RekadoApplication.instance.applicationContext.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(type, address)
        clipboard.primaryClip = clip

        dismiss()
        Toast.makeText(RekadoApplication.instance.applicationContext, RekadoApplication.instance.applicationContext.getString(R.string.donate_address_copied, type), Toast.LENGTH_SHORT).show()
    }
}
