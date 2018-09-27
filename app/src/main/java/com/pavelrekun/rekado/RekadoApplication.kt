package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import com.pavelrekun.rang.data.AccentColor
import com.pavelrekun.rang.data.NightMode
import com.pavelrekun.rang.data.PrimaryColor
import com.pavelrekun.rang.services.Rang
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import io.paperdb.Paper

@SuppressLint("StaticFieldLeak")
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

        prepareRang()
    }

    private fun prepareRang() {
        val defaultSetup = Rang.defaults().primaryColor(PrimaryColor.CASTRO).accentColor(AccentColor.LIGHT_BLUE_A400).nightMode(NightMode.NIGHT).oledMode(false)
        Rang.init(this, defaultSetup)
    }

}