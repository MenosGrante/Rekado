package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import flipagram.assetcopylib.AssetCopier
import org.greenrobot.eventbus.EventBus
import java.io.File

object MemoryUtils {

    fun copyBundledPayloads() {
        copyFile(PayloadHelper.BUNDLED_PAYLOAD_SX)
        copyFile(PayloadHelper.BUNDLED_PAYLOAD_REINX)
        copyFile(PayloadHelper.BUNDLED_PAYLOAD_HEKATE)

        EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
    }

    private fun copyFile(inputPath: String) {
        try {
            AssetCopier(RekadoApplication.context).withFileScanning().copy(inputPath, PayloadHelper.getLocation())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun removeFile(path: String) {
        File(path).delete()
    }

    fun toFile(file: File, path: String): File {
        return file.copyTo(File(path), true)
    }
}