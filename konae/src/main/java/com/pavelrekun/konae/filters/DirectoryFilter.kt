package com.pavelrekun.konae.filters

import java.io.File
import java.io.FileFilter

class DirectoryFilter : FileFilter {

    override fun accept(pathname: File): Boolean {
        if (pathname.isFile) {
            return false
        }

        if (pathname.isDirectory) {
            return true
        }

        return false
    }
}