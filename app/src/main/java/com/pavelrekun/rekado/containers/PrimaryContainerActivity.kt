package com.pavelrekun.rekado.containers

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.ui.setupWithNavController
import com.github.javiersantos.appupdater.AppUpdater
import com.github.javiersantos.appupdater.enums.Display
import com.github.javiersantos.appupdater.enums.UpdateFrom
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.databinding.ActivityContainerPrimaryBinding
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.extensions.openAboutScreen
import com.pavelrekun.rekado.services.extensions.openSettingsScreen
import com.pavelrekun.rekado.services.extensions.openTranslatorsScreen

class PrimaryContainerActivity : BaseActivity() {

    private lateinit var binding: ActivityContainerPrimaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContainerPrimaryBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        prepareNavigation(R.id.primaryLayoutContainer)

        initToolbar()
        initBottomNavigation()
        initUpdatesChecker()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_secondary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_about -> {
                openAboutScreen()
                true
            }

            R.id.navigation_settings -> {
                openSettingsScreen()
                true
            }

            R.id.navigation_donate -> {
                DialogsShower.showDonateDialog(this)
                true
            }

            R.id.navigation_translators -> {
                openTranslatorsScreen()
                true
            }

            else -> return false
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.primaryLayoutToolbar)
    }

    private fun initBottomNavigation() {
        binding.primaryLayoutNavigation.setupWithNavController(controller)
    }

    private fun initUpdatesChecker() {
        AppUpdater(this)
                .setUpdateFrom(UpdateFrom.JSON)
                .setUpdateJSON(Constants.UPDATE_CHANGELOG_LINK)
                .setDisplay(Display.NOTIFICATION)
                .start()
    }
}