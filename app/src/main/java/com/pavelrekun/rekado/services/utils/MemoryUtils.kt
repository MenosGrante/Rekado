package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus
import java.io.*

object MemoryUtils {

    fun copyAsset() {
        val assetManager = RekadoApplication.instance.applicationContext.assets
        val sxPayloadFile = assetManager.open(PayloadHelper.BASIC_PAYLOAD_NAME)

        copyFile(sxPayloadFile, FileOutputStream("${PayloadHelper.FOLDER_PATH}/${PayloadHelper.BASIC_PAYLOAD_NAME}"))

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