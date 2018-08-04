package com.pavelrekun.rekado.services.lakka

import android.os.Environment
import com.pavelrekun.rekado.services.utils.SettingsUtils
import java.io.File

object LakkaHelper {

    val FOLDER_PATH = "${Environment.getExternalStorageDirectory()}/Rekado/Lakka"

    const val PAYLOAD_FILENAME = "cbfs.bin"
    const val COREBOOT_FILENAME = "coreboot.rom"

    const val PAYLOAD_UPDATE_DATE = "01.07.2018"

    // TODO: For future releases
    fun checkCBFSPresent(): Boolean {
        return true
    }

    fun checkCorebootPresent(): Boolean {
        return File("$FOLDER_PATH/coreboot.rom").exists()
    }

    fun getCorebootUpdateDate(): String {
        return SettingsUtils.getCorebootUpdateDate().toString()
    }

}