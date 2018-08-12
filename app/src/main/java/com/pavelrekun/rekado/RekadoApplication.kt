package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import android.support.v7.app.AppCompatDelegate
import com.pavelrekun.rang.Rang
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

        Rang.defaults().primaryColor().accentColor().nightMode().oledMode()
        Rang.init(this)
    }

}