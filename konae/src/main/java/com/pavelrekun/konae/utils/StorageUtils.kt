package com.pavelrekun.konae.utils

import android.content.Context
import android.os.Environment
import java.io.File


object StorageUtils {

    fun checkExternalStoragePresent(context: Context): Boolean {
        return getExternalMemoryPaths(context) != null
    }

    fun getExternalMemoryPaths(context: Context): List<String?>? {
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

    private fun getRootOfInnerSdCardFolder(file: File?): String? {
        var file = file ?: return null

        val totalSpace = file.totalSpace

        while (true) {
            val parentFile = file.parentFile
            if (parentFile == null || parentFile!!.totalSpace !== totalSpace)
                return file.absolutePath
            file = parentFile
        }
    }


}