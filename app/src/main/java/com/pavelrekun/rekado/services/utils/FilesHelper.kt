package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus
import java.io.*


object FilesHelper {

    fun toFile(inputStream: InputStream, path: String) {
        File(path).outputStream().use { inputStream.copyTo(it) }
    }

    fun copyAsset() {
        val assetManager = RekadoApplication.instance.applicationContext.assets
        val sxPayloadFile = assetManager.open("sx_loader.bin")

        copyFile(sxPayloadFile, FileOutputStream("${PayloadHelper.FOLDER_PATH}/sx_loader.bin"))

        EventBus.getDefault().post(Events.UpdateListEvent())
    }

    @Throws(IOException::class)
    private fun copyFile(inputStream: InputStream, outputStream: OutputStream) {
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
    }

}