package com.pavelrekun.rekado.services.lakka

import android.os.Environment
import com.pavelrekun.rekado.services.utils.Utils
import java.io.File
import java.util.*

object LakkaHelper {

    val FOLDER_PATH = "${Environment.getExternalStorageDirectory()}/Rekado/Lakka"
    val COREBOOT_FILE_PATH = "$FOLDER_PATH/coreboot.rom"

    const val PAYLOAD_FILENAME = "cbfs.bin"
    const val COREBOOT_FILENAME = "coreboot.rom"

    const val PAYLOAD_UPDATE_DATE = "01.07.2018"

    // TODO: For future releases
    fun checkCBFSPresent(): Boolean {
        return true
    }

    fun checkCorebootPresent(): Boolean {
        return File(COREBOOT_FILE_PATH).exists()
    }

    fun getCorebootUpdateDate(): String {
        return Utils.formatDate(Date(File(COREBOOT_FILE_PATH).lastModified()))
    }

}