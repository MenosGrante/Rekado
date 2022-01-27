package com.pavelrekun.rekado.screens.navigation_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.databinding.ActivityNavigationBinding
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.extensions.dp
import com.pavelrekun.rekado.services.handlers.AppearanceHandler
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NavigationActivity : AppCompatActivity() {

    @Inject
    lateinit var appearanceHandler: AppearanceHandler

    private lateinit var controller: NavController
    private lateinit var binding: ActivityNavigationBinding

    private val destinationChangedListener =
            NavController.OnDestinationChangedListener { _, p1, arguments ->
                when (p1.id) {
                    R.id.navigationSettings,
                    R.id.navigationSerialChecker,
                    R.id.navigationAbout -> {
                        binding.navigationBottomBar.visibility = View.GONE

                        binding.navigationToolbar.titleMarginStart = 16.dp
                        binding.navigationToolbar.setNavigationIcon(R.drawable.ic_navigation_back)
                        binding.navigationToolbar.setNavigationOnClickListener {
                            onBackPressed()
                        }
                    }

                    else -> {
                        binding.navigationBottomBar.visibility = View.VISIBLE

                        binding.navigationToolbar.navigationIcon = null
                        binding.navigationToolbar.titleMarginStart = 24.dp
                    }
                }

                prepareToolbar(arguments?.getInt(NAVIGATION_TITLE) as Int)
            }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appearanceHandler.applySelectedTheme()
        appearanceHandler.applyEdgeToEdge(window)
        appearanceHandler.applyDynamicColors(this)

        binding = ActivityNavigationBinding.inflate(layoutInflater).apply { setContentView(this.root) }
        controller = findNavController(R.id.navigationFragmentContainer)

        prepareToolbar(R.string.about_developer_github_title)
        initBottomNavigation()
    }

    override fun onResume() {
        super.onResume()
        controller.addOnDestinationChangedListener(destinationChangedListener)
    }

    override fun onPause() {
        controller.removeOnDestinationChangedListener(destinationChangedListener)
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_secondary, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigation_about -> {
                controller.navigate(R.id.navigationAbout)
                true
            }

            R.id.navigation_settings -> {
                controller.navigate(R.id.navigationSettings)
                true
            }

            R.id.navigation_donate -> {
                DialogsShower.showDonateDialog(this)
                true
            }

            else -> return false
        }
    }

    private fun prepareToolbar(title: Int) {
        binding.navigationToolbar.setTitle(title)
        setSupportActionBar(binding.navigationToolbar)

        binding.navigationToolbar.setNavigationOnClickListener {
            if (!checkBackStackEmpty()) {
                onBackPressed()
            } else {
                finish()
            }
        }
    }

    private fun checkBackStackEmpty(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navigationFragmentContainer)
        val backStackEntryCount = navHostFragment?.childFragmentManager?.backStackEntryCount

        return backStackEntryCount != null && backStackEntryCount == 0
    }

    private fun initBottomNavigation() {
        binding.navigationBottomBar.setupWithNavController(controller)

        // Disable ability to reselect currently opened fragment
        binding.navigationBottomBar.setOnItemReselectedListener { }
    }

    fun navigate(id: Int) {
        controller.navigate(id)
    }

    fun showProgress() {
        binding.navigationProgress.visibility = View.VISIBLE
    }

    fun hideProgress() {
        binding.navigationProgress.visibility = View.GONE
    }

    companion object {

        private const val NAVIGATION_TITLE = "NAVIGATION_TITLE"

    }

}