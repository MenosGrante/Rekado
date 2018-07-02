package com.pavelrekun.rekado.services.payloads

import android.os.Environment
import com.orhanobut.hawk.Hawk
import com.pavelrekun.rekado.data.Payload
import java.io.File


object PayloadHelper {

    val FOLDER_PATH = "${Environment.getExternalStorageDirectory()}/Rekado/"

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    fun init() {
        val folderFile = File(FOLDER_PATH)
        if (!folderFile.exists()) folderFile.mkdir()
    }

    fun getPayloads(): MutableList<Payload> {
        val payloads: MutableList<Payload> = ArrayList()

        File(FOLDER_PATH).listFiles().forEach {
            if (it.extension == "bin") {
                payloads.add(Payload(getName(it.path), getPath(it.path)))
            }
        }

        return payloads
    }

    fun getName(path: String): String {
        return File(path).name
    }

    fun getPath(name: String): String {
        return "$FOLDER_PATH/$name"
    }

    fun putChosenPayload(payload: Payload) {
        Hawk.put(CHOSEN_PAYLOAD, payload)
    }

    fun getChosenPaylaod(): Payload {
        return Hawk.get(CHOSEN_PAYLOAD)
    }

    fun removeChosenPayload() {
        Hawk.delete(CHOSEN_PAYLOAD)
    }
}