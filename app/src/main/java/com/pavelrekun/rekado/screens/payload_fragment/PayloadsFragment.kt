package com.pavelrekun.rekado.screens.payload_fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentPayloadsBinding
import com.pavelrekun.rekado.services.constants.Mimes
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.extensions.extractFileName
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.handlers.PayloadsHandler
import com.pavelrekun.rekado.services.handlers.PreferencesHandler
import com.pavelrekun.rekado.services.handlers.StorageHandler
import com.pavelrekun.rekado.services.utils.LoginUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PayloadsFragment : BaseFragment(R.layout.fragment_payloads) {

    @Inject
    lateinit var preferencesHandler: PreferencesHandler

    @Inject
    lateinit var storageHandler: StorageHandler

    @Inject
    lateinit var payloadsHandler: PayloadsHandler

    private val binding by viewBinding(FragmentPayloadsBinding::bind)
    private val viewModel: PayloadsViewModel by viewModels()

    private lateinit var adapter: PayloadsAdapter

    private val addPayloadContract = registerForActivityResult(ActivityResultContracts.OpenDocument()) { data ->
        val name = data?.extractFileName(requireContext())
        if (name != null) {
            val inputStream = requireBaseActivity().contentResolver.openInputStream(data)

            if (inputStream != null) {
                storageHandler.copyPayload(inputStream, name)
                viewModel.updatePayloads()
                LoginUtils.info("Added new payload: $name")
            } else {
                Toast.makeText(requireContext(), R.string.payloads_add_external_error, Toast.LENGTH_SHORT).show()
                LoginUtils.error("Failed to add payload: $name")
            }
        } else {
            Toast.makeText(requireContext(), R.string.payloads_add_external_error, Toast.LENGTH_SHORT).show()
            LoginUtils.error("Failed to add selected payload!")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initData()
        initUpdates()
        initClickListeners()
    }

    private fun initObservers() {
        viewModel.isProgressShowing.observe(viewLifecycleOwner) { isShowing ->
            if (isShowing) showProgress() else hideProgress()
        }

        viewModel.errorResult.observe(viewLifecycleOwner) {
            DialogsShower.showPayloadsNetworkErrorDialog(requireContext(), it.message)
            LoginUtils.error("Error occurred: ${it.message}")

            it.printStackTrace()
        }

        viewModel.configFetchSuccess.observe(viewLifecycleOwner) { newConfig ->
            val currentConfig = preferencesHandler.getCurrentConfig()

            if (newConfig.revision > currentConfig.revision) {
                DialogsShower.showPayloadsUpdatesDialog(requireContext()) {
                    viewModel.updatePayloads(newConfig, currentConfig)
                }
            } else {
                DialogsShower.showPayloadsNoUpdatesDialog(requireContext())
            }
        }

        viewModel.downloadSuccess.observe(viewLifecycleOwner) { payloadTitle ->
            val message = getString(R.string.payloads_download_status_success, payloadTitle)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            LoginUtils.info(message)
        }

        viewModel.updateList.observe(viewLifecycleOwner) { updatePayloads() }
    }

    private fun initData() {
        adapter = PayloadsAdapter(payloadsHandler) { payloadToRemove ->
            storageHandler.removePayload(payloadToRemove)
            updatePayloads()
        }

        updatePayloads()

        binding.payloadsList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = this@PayloadsFragment.adapter
        }
    }

    private fun initUpdates() {
        binding.payloadsLayoutUpdate.visibility = if (preferencesHandler.checkHideBundledPayloadsEnabled()) View.GONE else View.VISIBLE
        binding.payloadsUpdateDescription.text = getString(R.string.payloads_update_bundled_description, getString(R.string.helper_bundled_payloads))

        binding.payloadsUpdateCheck.setOnClickListener {
            viewModel.fetchExternalConfig()
        }
    }

    private fun initClickListeners() {
        binding.payloadsDownload.setOnClickListener { DialogsShower.showPayloadsDownloadDialog(requireContext(), viewModel) }
        binding.payloadsAdd.setOnClickListener { addPayloadContract.launch(Mimes.BINARY.toTypedArray()) }
    }

    private fun updatePayloads() {
        if (this::adapter.isInitialized) {
            adapter.submitList(payloadsHandler.getAllPayloads())
        }
    }

}