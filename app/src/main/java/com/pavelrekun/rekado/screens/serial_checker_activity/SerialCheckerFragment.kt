package com.pavelrekun.rekado.screens.serial_checker_activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.zxing.integration.android.IntentIntegrator
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentSerialCheckerBinding
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.utils.SerialUtils
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.extensions.isEmpty
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.Utils

class SerialCheckerFragment : BaseFragment(R.layout.fragment_serial_checker) {

    private val binding by viewBinding(FragmentSerialCheckerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScrollingBehaviour(binding.serialCheckerLayoutScroll)
        initClickListeners()
        generateInformation()
    }

    private fun initClickListeners() {
        binding.serialCheckerCheck.setOnClickListener {
            if (binding.serialCheckerField.length() <= 14) {
                if (!binding.serialCheckerField.isEmpty()) {
                    try {
                        val text = SerialUtils.defineConsoleStatus(binding.serialCheckerField.getString())
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

        binding.serialCheckerHelp.setOnClickListener {
            Utils.openLink(getBaseActivity(), Constants.HELP_SERIAL_CHECKER)
        }

        binding.serialCheckerScan.setOnClickListener {
            IntentIntegrator(activity).apply {
                setOrientationLocked(false)
                setBeepEnabled(false)
            }.initiateScan()
        }
    }

    private fun generateInformation() {
        val serialsInformation = getString(R.string.serial_checker_information_xaw1) +
                getString(R.string.serial_checker_information_xaw4) +
                getString(R.string.serial_checker_information_xaw7) +
                getString(R.string.serial_checker_information_xaj1) +
                getString(R.string.serial_checker_information_xaj4) +
                getString(R.string.serial_checker_information_xaj7) +
                getString(R.string.serial_checker_information_xaw9) +
                getString(R.string.serial_checker_information_xak)
        binding.serialCheckerInformation.text = serialsInformation
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result.contents != null) {
            binding.serialCheckerField.setText(result.contents)
            binding.serialCheckerField.requestFocus()
        } else {
            Toast.makeText(activity, R.string.serial_checker_status_scan_failed, Toast.LENGTH_SHORT).show()
        }
    }

}