package com.pavelrekun.rekado.screens.main_activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.about_activity.AboutActivity
import com.pavelrekun.rekado.screens.settings_activity.SettingsActivity
import com.pavelrekun.rekado.services.dialogs.DonateDialog
import com.pavelrekun.rekado.services.utils.DesignUtils


class MainActivity : BaseActivity() {

    private lateinit var mvpView: MainContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpView = MainView(this, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mvpView.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        mvpView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mvpView.onStop()
    }
}