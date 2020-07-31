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
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_secondary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_about -> {
                controller.openAboutScreen()
                true
            }

            R.id.navigation_settings -> {
                controller.openSettingsScreen()
                true
            }

            R.id.navigation_donate -> {
                DialogsShower.showDonateDialog(this)
                true
            }

            R.id.navigation_check_for_updates -> {
                AppUpdater(this)
                        .setUpdateFrom(UpdateFrom.JSON)
                        .setUpdateJSON("https://raw.githubusercontent.com/MenosGrante/Rekado/master/app/update-changelog.json")
                        .setDisplay(Display.DIALOG)
                        .showAppUpdated(true)
                        .start()
                true
            }

            R.id.navigation_translators -> {
                controller.openTranslatorsScreen()
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
}