package com.pavelrekun.rekado.screens.instructions_fragment

import android.os.Bundle
import android.view.View
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentInstructionsBinding
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.Utils

class InstructionsFragment : BaseFragment(R.layout.fragment_instructions) {

    private val binding by viewBinding(FragmentInstructionsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWithTitle(R.string.navigation_instructions)
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.instructionsRCMHelp.setOnClickListener { Utils.openLink(getBaseActivity(), Constants.HELP_RCM) }
    }

}