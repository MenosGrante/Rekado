package com.pavelrekun.rekado.screens.tools_fragment

import android.os.Bundle
import android.view.View
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentToolsBinding
import com.pavelrekun.rekado.services.extensions.openToolsSerialCheckerScreen
import com.pavelrekun.rekado.services.extensions.viewBinding

class ToolsFragment : BaseFragment(R.layout.fragment_tools) {

    private val binding by viewBinding(FragmentToolsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initWithTitle(R.string.navigation_tools)
        initClickListeners()
    }

    private fun initClickListeners() {
        binding.toolsSerialCheckerLayout.setOnClickListener {
            getBaseActivity().controller.openToolsSerialCheckerScreen()
        }
    }

}