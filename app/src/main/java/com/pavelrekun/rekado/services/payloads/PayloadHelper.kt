package com.pavelrekun.rekado.services.payloads

import android.os.Environment
import android.widget.Toast
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.Logger
import io.paperdb.Paper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okio.Okio
import org.greenrobot.eventbus.EventBus
import java.io.File


object PayloadHelper {

    val FOLDER_PATH = "${Environment.getExternalStorageDirectory().absolutePath}/Rekado/"

    const val BUNDLED_PAYLOAD_SX = "sx_loader.bin"
    const val BUNDLED_PAYLOAD_REINX = "ReiNX.bin"
    const val BUNDLED_PAYLOAD_HEKATE = "hekate.bin"
    const val BUNDLED_PAYLOAD_HEKATE_OLD = "hekate [4.6].bin"

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    fun init() {
        val folderFile = File(FOLDER_PATH)
        if (!folderFile.exists()) folderFile.mkdirs()
    }

    fun getAll(): MutableList<Payload> {
        val payloads: MutableList<Payload> = ArrayList()

        File(FOLDER_PATH).listFiles() ?: return mutableListOf()

        File(FOLDER_PATH).listFiles().forEach {
            if (it.path.contains("bin")) {
                payloads.add(Payload(getName(it.path), it.path))
            }
        }

        return payloads
    }

    fun clearFolderWithoutBundled() {
        File(FOLDER_PATH).listFiles().forEach {
            if (it.name != BUNDLED_PAYLOAD_SX || it.name != BUNDLED_PAYLOAD_REINX || it.name != BUNDLED_PAYLOAD_HEKATE) {
                it.delete()
            }
        }
    }

    fun clearBundled() {
        File(FOLDER_PATH).listFiles().forEach {
            if (it.name == BUNDLED_PAYLOAD_SX || it.name == BUNDLED_PAYLOAD_REINX || it.name == BUNDLED_PAYLOAD_HEKATE) {
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

    fun getRootDirectory(): File {
        val file = File(FOLDER_PATH)

        if (!file.exists()) {
            file.mkdirs()
        }

        return file
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

    fun downloadPayload(activity: BaseActivity, name: String, url: String) = GlobalScope.launch(Dispatchers.Main) {
        val properName = if (name.endsWith(".bin")) name else "$name.bin"
        val httpClient = OkHttpClient()

        try {
            withContext(Dispatchers.Default) {
                val request = Request.Builder()
                        .url(url)
                        .build()

                val response = httpClient
                        .newCall(request)
                        .execute()
                        .body()

                val contentType = response?.contentType()?.subtype()

                if (response != null && contentType != null && contentType == "octet-stream") {
                    Logger.info("Downloading payload: $properName.")

                    val targetPlace = File(getRootDirectory(), properName)

                    if (!targetPlace.exists()) {
                        targetPlace.mkdirs()
                    }

                    val sink = Okio.buffer(Okio.sink(targetPlace))
                    sink.writeAll(response.source())
                    sink.close()

                    response.close()

                    EventBus.getDefault().post(Events.PayloadDownloadedSuccessfully(properName))
                } else {
                    throw Exception()
                }
            }
        } catch (e: Exception) {
            Toast.makeText(activity, "Failed to download payload. Check your internet connection or typos in URL.", Toast.LENGTH_SHORT).show()
            Logger.error("Failed to download payload: $properName.")
        }
    }

}