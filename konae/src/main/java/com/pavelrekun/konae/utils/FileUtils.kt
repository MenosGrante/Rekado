package com.pavelrekun.konae.utils

import java.io.File
import java.text.DecimalFormat

object FileUtils {

    fun getExtension(file: File?): String? {
        return file?.extension
    }

    fun getReadableFileSize(size: Long): String {
        val BYTES_IN_KILOBYTES = 1024

        val dec = DecimalFormat("###.#")

        val KILOBYTES = " KB"
        val MEGABYTES = " MB"
        val GIGABYTES = " GB"

        var fileSize = 0f

        var suffix = KILOBYTES

        if (size > BYTES_IN_KILOBYTES) {
            fileSize = (size / BYTES_IN_KILOBYTES).toFloat()
            if (fileSize > BYTES_IN_KILOBYTES) {
                fileSize /= BYTES_IN_KILOBYTES
                if (fileSize > BYTES_IN_KILOBYTES) {
                    fileSize /= BYTES_IN_KILOBYTES
                    suffix = GIGABYTES
                } else {
                    suffix = MEGABYTES
                }
            }
        }
        return (dec.format(fileSize) + suffix)
    }


}