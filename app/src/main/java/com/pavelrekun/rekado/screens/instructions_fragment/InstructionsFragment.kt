package com.pavelrekun.rekado.screens.instructions_fragment

import android.os.Bundle
import android.view.View
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentInstructionsBinding
import com.pavelrekun.rekado.services.constants.Links
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.Utils

class InstructionsFragment : BaseFragment(R.layout.fragment_instructions) {

    private val binding by viewBinding(FragmentInstructionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initClickListeners()
        initStrings()
    }

    private fun initClickListeners() {
        binding.instructionsRCMHelp.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.HELP_RCM) }
    }

    private fun initStrings() {
        binding.instructionsPayloadDescription.text = getString(R.string.instructions_category_payload_description, getString(R.string.helper_bundled_payloads))
    }

}