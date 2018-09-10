package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus
import java.io.*

object MemoryUtils {

    fun copyBundledPayloads() {
        val assetManager = RekadoApplication.instance.applicationContext.assets

        val sxPayloadFile = assetManager.open(PayloadHelper.BUNDLED_PAYLOAD_SX)
        val reiNXPayloadFile = assetManager.open(PayloadHelper.BUNDLED_PAYLOAD_REINX)

        copyFile(sxPayloadFile, FileOutputStream("${PayloadHelper.FOLDER_PATH}/${PayloadHelper.BUNDLED_PAYLOAD_SX}"))
        copyFile(reiNXPayloadFile, FileOutputStream("${PayloadHelper.FOLDER_PATH}/${PayloadHelper.BUNDLED_PAYLOAD_REINX}"))

        EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

    fun removeFile(path: String) {
        File(path).delete()
    }

    fun toFile(file: File, path: String): File {
        return file.copyTo(File(path), true)
    }
}