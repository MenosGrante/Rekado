package com.pavelrekun.rekado.base

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.pavelrekun.magta.design.resolveColorAttribute
import com.pavelrekun.magta.design.tintNavigationBar
import com.pavelrekun.magta.design.tintTaskDescription
import com.pavelrekun.penza.base.PenzaActivity
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.containers.PrimaryContainerActivity

@SuppressLint("Registered")
open class BaseActivity : PenzaActivity() {

    open lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initDesignRules()
    }

    override fun setTitle(titleResId: Int) {
        supportActionBar?.title = getString(titleResId)
    }

    fun setTitle(title: String) {
        supportActionBar?.title = title
    }

    fun prepareNavigation(id: Int) {
        this.controller = findNavController(id)
    }

    private fun initDesignRules() {
        tintTaskDescription(R.mipmap.ic_launcher, R.string.app_name, resolveColorAttribute(android.R.attr.windowBackground))

        if (this is PrimaryContainerActivity) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
                tintNavigationBar(resolveColorAttribute(R.attr.colorBackgroundSecondary))
            }
        }
    }
}