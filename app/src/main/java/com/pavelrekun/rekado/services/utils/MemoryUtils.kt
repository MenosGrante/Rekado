package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.extensions.toFile
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus

object MemoryUtils {

    fun copyBundledPayloads() {
        copyAsset(PayloadHelper.BUNDLED_PAYLOAD_SX)
        copyAsset(PayloadHelper.BUNDLED_PAYLOAD_REINX)
        copyAsset(PayloadHelper.BUNDLED_PAYLOAD_HEKATE)
        copyAsset(PayloadHelper.BUNDLED_PAYLOAD_FUSEE_PRIMARY)

        EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
    }

    private fun copyAsset(name: String) {
        val assetsManager = RekadoApplication.context.assets
        assetsManager.open(name).toFile("${PayloadHelper.getLocation().absolutePath}/$name")
    }
}