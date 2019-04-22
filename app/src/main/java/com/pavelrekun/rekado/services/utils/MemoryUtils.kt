package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import flipagram.assetcopylib.AssetCopier
import org.greenrobot.eventbus.EventBus
import java.io.File

object MemoryUtils {

    fun copyBundledPayloads() {
        removeOldFiles()


        copyFile(PayloadHelper.BUNDLED_PAYLOAD_HEKATE)

        EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
    }

    private fun removeOldFiles() {
        val oldVersionHekate = File(PayloadHelper.BUNDLED_PAYLOAD_HEKATE_OLD)
        if (oldVersionHekate.exists()) {
            oldVersionHekate.delete()
        }
    }

    private fun copyFile(inputPath: String) {
        try {
            AssetCopier(RekadoApplication.instance.applicationContext)
                    .withFileScanning()
                    .copy(inputPath, File(PayloadHelper.FOLDER_PATH))
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