package com.pavelrekun.rekado.screens.serial_checker_activity

import android.content.Intent
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.definiers.SerialDefinier
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.extensions.isEmpty
import com.pavelrekun.rekado.services.utils.Utils
import kotlinx.android.synthetic.main.activity_serial_checker.*

class SerialCheckerView(private val activity: BaseActivity) : SerialCheckerContract.View {

    init {
        onViewCreated()
    }

    override fun onViewCreated() {
        initToolbar()
        initClickListeners()

        generateInformation()
    }

    override fun initToolbar() {
        activity.serialCheckerToolbar.setTitle(R.string.navigation_serial_checker)
        activity.setSupportActionBar(activity.serialCheckerToolbar)

        activity.serialCheckerToolbar.setNavigationIcon(R.drawable.ic_navigation_back)
        activity.serialCheckerToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    override fun initClickListeners() {
        activity.serialCheckerCheck.setOnClickListener {
            if (activity.serialCheckerField.length() <= 14) {
                if (!activity.serialCheckerField.isEmpty()) {
                    try {
                        val text = SerialDefinier.defineConsoleStatus(activity.serialCheckerField.getString())
                        Toast.makeText(activity, text, Toast.LENGTH_SHORT).show()
                    } catch (e: Exception) {
                        Toast.makeText(activity, R.string.serial_checker_status_error, Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(activity, R.string.serial_checker_status_empty, Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, R.string.serial_checker_status_too_long, Toast.LENGTH_SHORT).show()
            }
        }

        activity.serialCheckerHelp.setOnClickListener {
            Utils.openLink(activity, Constants.HELP_SERIAL_CHECKER)
        }

        activity.serialCheckerScan.setOnClickListener {
            IntentIntegrator(activity).apply {
                setOrientationLocked(false)
                setBeepEnabled(false)
            }.initiateScan()
        }
    }

    override fun generateInformation() {
        val serialsInformation = activity.getString(R.string.serial_checker_information_xaw1) + activity.getString(R.string.serial_checker_information_xaw4) + activity.getString(R.string.serial_checker_information_xaw7) + activity.getString(R.string.serial_checker_information_xaj1) + activity.getString(R.string.serial_checker_information_xaj4) + activity.getString(R.string.serial_checker_information_xaj7) + activity.getString(R.string.serial_checker_information_xaw9) + activity.getString(R.string.serial_checker_information_xak)
        activity.serialCheckerInformation.text = serialsInformation
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result.contents != null) {
            activity.serialCheckerField.setText(result.contents)
            activity.serialCheckerField.requestFocus()
        } else {
            Toast.makeText(activity, R.string.serial_checker_status_scan_failed, Toast.LENGTH_SHORT).show()
        }
    }
}