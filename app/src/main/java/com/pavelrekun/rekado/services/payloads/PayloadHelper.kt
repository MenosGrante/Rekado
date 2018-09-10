package com.pavelrekun.rekado.services.payloads

import android.os.Environment
import com.pavelrekun.rekado.data.Payload
import io.paperdb.Paper
import java.io.File


object PayloadHelper {

    val FOLDER_PATH = "${Environment.getExternalStorageDirectory()}/Rekado/"

    const val BUNDLED_PAYLOAD_SX = "sx_loader.bin"
    const val BUNDLED_PAYLOAD_REINX = "ReiNX.bin"

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    fun init() {
        val folderFile = File(FOLDER_PATH)
        if (!folderFile.exists()) folderFile.mkdirs()
    }

    fun getAll(): MutableList<Payload> {
        val payloads: MutableList<Payload> = ArrayList()

        File(FOLDER_PATH).listFiles().forEach {
            if (it.path.contains("bin")) {
                payloads.add(Payload(getName(it.path), it.path))
            }
        }

        return payloads
    }

    fun clearFolder() {
        File(FOLDER_PATH).listFiles().forEach {
            if (it.name != BUNDLED_PAYLOAD_SX || it.name != BUNDLED_PAYLOAD_REINX) {
                it.delete()
            }
        }
    }

    fun getNames(): MutableList<String> {
        val payloads: MutableList<String> = ArrayList()

        for (payload in getAll()) {
            payloads.add(payload.name)
        }

        return payloads
    }

    fun getName(path: String): String {
        return File(path).name
    }

    fun getPath(name: String): String {
        return "$FOLDER_PATH/$name"
    }

    fun find(name: String): Payload? {
        for (payload in getAll()) {
            if (payload.name == name) {
                return payload
            }
        }

        return null
    }

    fun putChosen(payload: Payload) {
        Paper.book().write(CHOSEN_PAYLOAD, payload)
    }

    fun getChosen(): Payload {
        return Paper.book().read(CHOSEN_PAYLOAD)
    }
}