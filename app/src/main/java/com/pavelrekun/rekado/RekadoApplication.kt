package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.pavelrekun.rekado.services.Logger
import com.pavelrekun.siga.Siga
import com.pavelrekun.siga.data.Color
import com.pavelrekun.siga.data.Theme

class RekadoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        configureInternalSystems()
        configureThemeEngine()
    }

    private fun configureThemeEngine() {
        val defaultSetup = Siga.createDefaults().theme(Theme.BLACK).color(Color.LIGHT_BLUE_500)
        Siga.init(this, defaultSetup)
    }

    private fun configureInternalSystems() {
        Logger.init(false)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

}