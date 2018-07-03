package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import com.pavelrekun.rekado.services.logs.Logger
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

        Logger.init()
        PayloadHelper.init()
    }

}