package com.pavelrekun.rekado.services.handlers

import com.pavelrekun.rekado.data.Payload

class PayloadsHandler(private val preferencesHandler: PreferencesHandler,
                      private val storageHandler: StorageHandler) {

    fun getAllPayloads(): MutableList<Payload> {
        return if (preferencesHandler.checkHideBundledPayloadsEnabled()) {
            getExternalPayloads().toMutableList()
        } else {
            (preferencesHandler.getCurrentConfig().payloads + getExternalPayloads()).toMutableList()
        }
    }

    fun checkPayloadsExists() = getAllPayloads().isNotEmpty()

    fun getTitles() = getAllPayloads().map { it.title }

    fun deletePayloads() = getAllFiles().map { if (!isBundled(it.name)) it.delete() }

    fun find(title: String) = getAllPayloads().find { it.title == title } as Payload

    fun isBundled(title: String) = BUNDLED_PAYLOADS.contains(title)

    fun getPayloadPath(payload: Payload) = storageHandler.getLocation().absolutePath + "/" + payload.title

    private fun getAllFiles() = storageHandler.getLocation().listFiles()!!.filter { it != null && it.extension == "bin" }.toMutableList()

    private fun getExternalPayloads() = getAllFiles()
            .filter { !BUNDLED_PAYLOADS.contains(it.name) }
            .map { Payload(title = it.name, null, null) }

    companion object {

        val BUNDLED_PAYLOADS = listOf("hekate.bin", "fusee.bin")

    }

}