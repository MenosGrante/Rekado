package com.pavelrekun.rekado.services.payloads

import android.os.Environment
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.eventbus.Events
import io.paperdb.Paper
import org.greenrobot.eventbus.EventBus
import java.io.File


object PayloadHelper {

    val FOLDER_PATH = "${Environment.getExternalStorageDirectory()}/Rekado/"
    const val BASIC_PAYLOAD_NAME = "sx_loader.bin"

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    fun init() {
        val folderFile = File(FOLDER_PATH)
        if (!folderFile.exists()) folderFile.mkdir()
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
            if (it.name != BASIC_PAYLOAD_NAME) {
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

    fun removeChosen() {
        Paper.book().delete(CHOSEN_PAYLOAD)
    }
}