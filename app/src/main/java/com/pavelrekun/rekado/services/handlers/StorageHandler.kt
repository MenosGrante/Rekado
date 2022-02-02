package com.pavelrekun.rekado.services.handlers

import android.content.Context
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.extensions.readConfig
import com.pavelrekun.rekado.services.extensions.toFile
import com.pavelrekun.rekado.services.utils.LoginUtils
import java.io.File
import java.io.InputStream

class StorageHandler(private val context: Context,
                     private val preferencesHandler: PreferencesHandler) {

    fun parseBundledConfig() {
        val currentConfigRaw = preferencesHandler.getCurrentConfigRaw()

        // Define if user coming from pre-5.0 version config
        // and if yes, erase previously saved config
        if (!currentConfigRaw.isNullOrEmpty() && currentConfigRaw.contains("timestamp")) {
            preferencesHandler.eraseCurrentConfig()
        }

        if (preferencesHandler.checkConfigExists()) {
            val currentConfig = preferencesHandler.getCurrentConfig()
            val bundledConfig = context.resources.readConfig(R.raw.config)

            if (bundledConfig.revision > currentConfig.revision) {
                preferencesHandler.saveConfig(bundledConfig)
                copyBundledPayloads()
            }
        } else {
            val config = context.resources.readConfig(R.raw.config)
            preferencesHandler.saveConfig(config)
            copyBundledPayloads()
        }
    }

    fun copyPayload(inputStream: InputStream, file: String) {
        inputStream.toFile("${getLocation().absolutePath}/$file")
    }

    fun removePayload(payload: Payload) {
        File(getLocation().absolutePath + "/" + payload.title).delete()
        LoginUtils.info("Payload ${payload.title} deleted!")
    }

    fun getLocation(): File {
        return context.getExternalFilesDir(null) ?: context.filesDir
    }

    private fun copyBundledPayloads() {
        copyPayload(context.resources.openRawResource(R.raw.fusee), "fusee.bin")
        copyPayload(context.resources.openRawResource(R.raw.hekate), "hekate.bin")
    }


}