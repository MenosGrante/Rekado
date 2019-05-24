package com.pavelrekun.rekado.screens.tools_fragment

import android.content.Intent
import android.view.View
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.serial_checker_activity.SerialCheckerActivity
import kotlinx.android.synthetic.main.fragment_tools.view.*

class ToolsView(private val activity: BaseActivity, private val view: View) : ToolsContract.View {

    override fun onViewCreated() {
        activity.setTitle(R.string.navigation_tools)

        initClickListeners()
    }

    override fun initClickListeners() {
        view.toolsSerialCheckerLayout.setOnClickListener { activity.startActivity(Intent(activity, SerialCheckerActivity::class.java)) }
    }
}