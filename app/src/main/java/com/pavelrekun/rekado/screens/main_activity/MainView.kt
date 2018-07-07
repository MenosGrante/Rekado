package com.pavelrekun.rekado.screens.main_activity

import android.support.v4.app.Fragment
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.instructions_fragment.InstructionsFragment
import com.pavelrekun.rekado.screens.logs_fragment.LogsFragment
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainView(private val activity: BaseActivity) : MainContract.View {

    init {
        initViews()
    }

    override fun initViews() {
        initNavigationClickListener()
        initToolbar()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.mainToolbar)
    }

    override fun initNavigationClickListener() {
        chooseNavigationItem(R.id.navigation_instructions)
        activity.mainNavigationBar.selectedItemId = R.id.navigation_instructions

        activity.mainNavigationBar.setOnNavigationItemSelectedListener {
            chooseNavigationItem(it.itemId)
            true
        }
    }

    private fun chooseNavigationItem(id: Int) {
        var fragment: Fragment? = null

        when (id) {
            R.id.navigation_payloads -> fragment = PayloadsFragment()
            R.id.navigation_instructions -> fragment = InstructionsFragment()
            R.id.navigation_logs -> fragment = LogsFragment()
        }

        if (fragment != null) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_content_frame, fragment)
            transaction.commitNow()
        }
    }
}