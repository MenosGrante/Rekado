package com.pavelrekun.rekado.services.lakka

object LakkaHelper {

    const val PAYLOAD_FILENAME = "cbfs.bin"
    const val COREBOOT_FILENAME = "coreboot.rom"

    const val PAYLOAD_UPDATE_DATE = "01.07.2018"
    const val COREBOOT_UPDATE_DATE = "03.08.2018"

    // TODO: For future releases
    fun checkCBFSPresent(): Boolean {
        return true
    }

    // TODO: For future releases
    fun checkCorebootPresent(): Boolean {
        return true
    }

}