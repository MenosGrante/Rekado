package com.pavelrekun.rekado

import android.app.Application
import com.pavelrekun.rekado.services.handlers.StorageHandler
import com.pavelrekun.rekado.services.utils.LoginUtils
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class Rekado : Application() {

    @Inject
    lateinit var storageHandler: StorageHandler

    override fun onCreate() {
        super.onCreate()

        storageHandler.parseBundledConfig()

        configureInternalSystems()
    }

    private fun configureInternalSystems() {
        LoginUtils.init(false)
    }

}