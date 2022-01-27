package com.pavelrekun.rekado.screens.serial_checker_activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.text.HtmlCompat
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentSerialCheckerBinding
import com.pavelrekun.rekado.services.constants.Links
import com.pavelrekun.rekado.services.extensions.getString
import com.pavelrekun.rekado.services.extensions.isEmpty
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.handlers.SerialNumberHandler
import com.pavelrekun.rekado.services.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import dev.chrisbanes.insetter.applyInsetter
import javax.inject.Inject

@AndroidEntryPoint
class SerialCheckerFragment : BaseFragment(R.layout.fragment_serial_checker) {

    @Inject
    lateinit var serialNumberHandler: SerialNumberHandler

    private val binding by viewBinding(FragmentSerialCheckerBinding::bind)

    private val barcodeScannerLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents != null) {
            binding.serialCheckerField.setText(result.contents)
            binding.serialCheckerField.requestFocus()
        } else {
            Toast.makeText(activity, R.string.serial_checker_status_scan_failed, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListeners()
        initEdgeToEdge()

        generateInformation()
    }

    private fun initClickListeners() {
        binding.serialCheckerCheck.setOnClickListener {
            if (binding.serialCheckerField.length() <= 14) {
                if (!binding.serialCheckerField.isEmpty()) {
                    try {
                        val text = serialNumberHandler.defineConsoleStatus(binding.serialCheckerField.getString())
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
            Utils.openLink(requireBaseActivity(), Links.HELP_SERIAL_CHECKER)
        }

        binding.serialCheckerScan.setOnClickListener {
            val scanOptions = ScanOptions().apply {
                setDesiredBarcodeFormats(ScanOptions.ONE_D_CODE_TYPES)
                setOrientationLocked(false)
                setBeepEnabled(false)
            }

            barcodeScannerLauncher.launch(scanOptions)
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

        binding.serialCheckerInformation.text = HtmlCompat.fromHtml(serialsInformation, HtmlCompat.FROM_HTML_MODE_LEGACY)
    }

    private fun initEdgeToEdge() {
        binding.serialCheckerLayoutContainer.applyInsetter {
            type(navigationBars = true) {
                padding()
            }
        }
    }


}