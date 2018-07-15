package com.pavelrekun.rekado.services.utils

import android.os.Environment
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

    fun removeFile(path: String) {
        File(path).delete()
    }

    fun checkExternalMemoryPresent() = getExternalMemoryPaths() != null

    private fun getExternalMemoryPaths(): ArrayList<String?>? {
        val context = RekadoApplication.instance.applicationContext
        val externalCacheDirs = context.externalCacheDirs

        if (externalCacheDirs == null || externalCacheDirs.isEmpty())
            return null

        if (externalCacheDirs.size == 1) {
            if (externalCacheDirs[0] == null)
                return null

            val storageState = Environment.getExternalStorageState(externalCacheDirs[0])

            if (Environment.MEDIA_MOUNTED != storageState)
                return null

            if (Environment.isExternalStorageEmulated())
                return null
        }

        val result = ArrayList<String?>()

        if (externalCacheDirs.size == 1)
            result.add(getRootOfInnerSdCardFolder(externalCacheDirs[0]))

        for (i in 1 until externalCacheDirs.size) {
            val file = externalCacheDirs[i] ?: continue

            val storageState = Environment.getExternalStorageState(file)

            if (Environment.MEDIA_MOUNTED == storageState)
                result.add(getRootOfInnerSdCardFolder(externalCacheDirs[i]))
        }

        return if (result.isEmpty()) null else result

    }

    private fun getRootOfInnerSdCardFolder(currentFile: File?): String? {
        var file = currentFile ?: return null

        val totalSpace = file.totalSpace

        while (true) {
            val parentFile = file.parentFile

            if (parentFile == null || parentFile.totalSpace != totalSpace)
                return file.absolutePath

            file = parentFile
        }
    }
}

fun File.toFile(path: String) {
    this.copyTo(File(path), true)
}