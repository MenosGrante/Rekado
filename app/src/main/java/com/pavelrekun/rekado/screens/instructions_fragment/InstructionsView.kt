package com.pavelrekun.rekado.screens.instructions_fragment

import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.utils.Utils
import kotlinx.android.synthetic.main.fragment_instrutions.*

class InstructionsView(private val activity: BaseActivity) : InstructionsContract.View {

    override fun initViews() {
        activity.setTitle(R.string.navigation_instructions)

        initClickListeners()
    }

    override fun initClickListeners() {
        activity.instructionsRCMHelp.setOnClickListener { Utils.openLink(Constants.HELP_RCM) }
    }
}