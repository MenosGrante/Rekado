package com.pavelrekun.rekado.containers

import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.databinding.ActivityContainerSecondaryBinding
import com.pavelrekun.rekado.services.extensions.*

class SecondaryContainerActivity : BaseActivity() {

    private lateinit var binding: ActivityContainerSecondaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityContainerSecondaryBinding.inflate(layoutInflater).apply { setContentView(this.root) }

        prepareToolbar()
        prepareNavigation(R.id.secondaryContainer)
        prepareDestination()
    }

    private fun prepareDestination() {
        when (intent.getIntExtra(NAVIGATION_TYPE, NAVIGATION_DESTINATION_UNKNOWN)) {
            NAVIGATION_DESTINATION_SETTINGS -> controller.navigate(R.id.navigationSettings)
            NAVIGATION_DESTINATION_ABOUT -> controller.navigate(R.id.navigationAbout)
            NAVIGATION_DESTINATION_TRANSLATORS -> controller.navigate(R.id.navigationTranslators)
            NAVIGATION_DESTINATION_TOOLS_SERIAL_CHECKER -> controller.navigate(R.id.navigationSerialChecker)
        }
    }

    private fun prepareToolbar() {
        val title = intent.getStringExtra(NAVIGATION_TITLE) as String
        binding.secondaryToolbar.title = title
        setSupportActionBar(binding.secondaryToolbar)
        binding.secondaryToolbar.setNavigationOnClickListener { onBackPressed() }
    }

}