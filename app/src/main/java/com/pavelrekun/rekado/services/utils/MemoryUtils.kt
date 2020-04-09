package com.pavelrekun.rekado.services.utils

import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.extensions.parseSchema
import com.pavelrekun.rekado.services.extensions.toFile
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.InputStream

object MemoryUtils {

    private val resources = RekadoApplication.context.resources

    fun parseBundledSchema() {
        if (!PreferencesUtils.checkSchemaExists()) {
            val schema = resources.openRawResource(R.raw.bundled_payloads).parseSchema()
            PreferencesUtils.saveSchema(schema)
            copyBundledPayloads()
        }
    }

    fun copyPayload(inputStream: InputStream, file: String) {
        inputStream.toFile("${getLocation().absolutePath}/$file")
    }

    fun getLocation(): File {
        return RekadoApplication.context.getExternalFilesDir(null)
                ?: RekadoApplication.context.filesDir
    }

    private fun copyBundledPayloads() {
        copyPayload(resources.openRawResource(R.raw.fusee_primary), "fusee_primary.bin")
        copyPayload(resources.openRawResource(R.raw.hekate), "hekate.bin")
        copyPayload(resources.openRawResource(R.raw.sx_loader), "sx_loader.bin")

        EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
    }


}