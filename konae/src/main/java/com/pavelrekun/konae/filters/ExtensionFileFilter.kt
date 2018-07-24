package com.pavelrekun.konae.filters

import com.pavelrekun.konae.utils.FileUtils
import java.io.File
import java.io.FileFilter

class ExtensionFileFilter(private val extension: String) : FileFilter {

    override fun accept(pathname: File): Boolean {

        if (extension.isEmpty()) {
            return true
        }

        if (pathname.isDirectory) {
            return true
        }

        val fileExtension = FileUtils.getExtension(pathname)

        if (extension.equals(fileExtension, ignoreCase = true)) {
            return true
        }

        return false
    }
}