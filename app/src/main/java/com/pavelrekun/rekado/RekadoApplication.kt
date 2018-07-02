package com.pavelrekun.rekado

import android.annotation.SuppressLint
import android.app.Application
import com.orhanobut.hawk.Hawk
import com.pavelrekun.rekado.services.logs.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper

@SuppressLint("StaticFieldLeak")
class RekadoApplication : Application() {

    companion object {
        lateinit var instance: RekadoApplication
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        Hawk.init(this).build()

        Logger.init()
        PayloadHelper.init()
    }

}