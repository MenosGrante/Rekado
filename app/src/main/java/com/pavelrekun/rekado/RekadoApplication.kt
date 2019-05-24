package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.pavelrekun.rekado.services.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.siga.data.Color
import com.pavelrekun.siga.data.Theme
import com.pavelrekun.siga.services.Siga
import io.paperdb.Paper

class RekadoApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext

        configureDatabase()
        configureInternalSystems()
        configureThemeEngine()
    }

    private fun configureThemeEngine() {
        val defaultSetup = Siga.createDefaults().theme(Theme.DARK_DEFAULT).color(Color.LIGHT_BLUE_500)
        Siga.init(this, defaultSetup)
    }

    private fun configureInternalSystems() {
        Logger.init()
        PayloadHelper.init()
    }

    private fun configureDatabase() {
        Paper.init(this)
    }

    companion object {

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context

    }

}