package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.siga.data.Color
import com.pavelrekun.siga.data.Theme
import com.pavelrekun.siga.services.Siga
import io.paperdb.Paper

@SuppressLint("ALL")
class RekadoApplication : Application() {

    companion object {
        lateinit var instance: RekadoApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Paper.init(this)

        LogHelper.init()
        PayloadHelper.init()

        configureThemeEngine()
    }

    private fun configureThemeEngine() {
        val defaultSetup = Siga.createDefaults().theme(Theme.DARK_DEFAULT).accentColor(Color.LIGHT_BLUE_500)
        Siga.init(this, defaultSetup)
    }

}