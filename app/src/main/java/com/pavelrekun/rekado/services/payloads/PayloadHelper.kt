package com.pavelrekun.rekado.services.payloads

import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.utils.MemoryUtils.getLocation
import com.pavelrekun.rekado.services.utils.PreferencesUtils

object PayloadHelper {

    val BUNDLED_PAYLOADS = listOf("hekate.bin", "sx_loader.bin", "fusee_primary.bin")

    fun getAllPayloads() = (PreferencesUtils.getCurrentSchema().payloads + getExternalPayloads()).toMutableList()

    fun getTitles() = getAllPayloads().map { it.title }

    fun deletePayloads() = getAllFiles().map { if (!isBundled(it.name)) it.delete() }

    fun find(title: String) = getAllPayloads().find { it.title == title } as Payload

    fun isBundled(title: String) = BUNDLED_PAYLOADS.contains(title)

    private fun getAllFiles() = getLocation().listFiles()!!.filter { it != null && it.extension == "bin" }.toMutableList()

    private fun getExternalPayloads() = getAllFiles()
            .filter { !BUNDLED_PAYLOADS.contains(it.name) }
            .map { Payload(title = it.name) }

}