package com.pavelrekun.rekado.screens.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.about_activity.AboutActivity
import com.pavelrekun.rekado.screens.instructions_fragment.InstructionsFragment
import com.pavelrekun.rekado.screens.logs_fragment.LogsFragment
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsFragment
import com.pavelrekun.rekado.screens.settings_activity.SettingsActivity
import com.pavelrekun.rekado.screens.tools_fragment.ToolsFragment
import com.pavelrekun.rekado.screens.translators_activity.TranslatorsActivity
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import kotlinx.android.synthetic.main.activity_main.*

class MainView(private val activity: BaseActivity, private val savedInstanceState: Bundle?) : MainContract.View {

    init {
        initViews()
    }

    override fun initViews() {
        initToolbar()
        initNavigationClickListener()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.mainToolbar)
    }

    override fun initNavigationClickListener() {
        if (savedInstanceState == null) {
            chooseNavigationItem(R.id.navigationInstructions)
            activity.mainNavigationBar.selectedItemId = R.id.navigationInstructions
        }

        activity.mainNavigationBar.setOnNavigationItemSelectedListener {
            chooseNavigationItem(it.itemId)
            true
        }

        activity.mainNavigationBar.setOnNavigationItemReselectedListener { }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_about -> {
                activity.startActivity(Intent(activity, AboutActivity::class.java))
                true
            }

            R.id.navigation_settings -> {
                activity.startActivity(Intent(activity, SettingsActivity::class.java))
                true
            }

            R.id.navigation_donate -> {
                DialogsShower.showDonateDialog(activity)
                true
            }

            R.id.navigation_translators -> {
                activity.startActivity(Intent(activity, TranslatorsActivity::class.java))
                true
            }

            else -> return false
        }
    }

    private fun chooseNavigationItem(id: Int) {
        var fragment: Fragment? = null

        when (id) {
            R.id.navigationPayloads -> fragment = PayloadsFragment()
            R.id.navigationTools -> fragment = ToolsFragment()
            R.id.navigationInstructions -> fragment = InstructionsFragment()
            R.id.navigationLogs -> fragment = LogsFragment()
        }

        if (fragment != null) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.mainFragmentFrame, fragment)
            transaction.commit()
        }
    }
}