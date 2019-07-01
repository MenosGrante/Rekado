package com.pavelrekun.rekado.services.payloads

import android.widget.Toast
import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
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
import okio.buffer
import okio.sink
import org.greenrobot.eventbus.EventBus
import java.io.File


object PayloadHelper {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.context)

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    const val BUNDLED_PAYLOAD_SX = "sx_loader.bin"
    const val BUNDLED_PAYLOAD_REINX = "ReiNX.bin"
    const val BUNDLED_PAYLOAD_HEKATE = "hekate.bin"

    fun getLocation(): File {
        return RekadoApplication.context.getExternalFilesDir(null) ?: RekadoApplication.context.filesDir
    }

    fun getAllPayloads() = getAllFiles().map { Payload(it.name, it.path) }.toMutableList()

    fun clearFolderWithoutBundled() {
        getAllFiles().map { if (isNotBundledPayload(Payload(it.name, it.path))) it.delete() }
    }

    fun clearBundled() {
        getAllFiles().map { if (isBundledPayload(Payload(it.name, it.path))) it.delete() }
    }

    fun getNames() = getAllFiles().map { it.name }.toMutableList()

    fun getPath(name: String): String {
        return "${getLocation()}/$name"
    }

    fun find(name: String): Payload? {
        return getAllPayloads().singleOrNull { it.name == name }
    }

    fun putChosen(payload: Payload) = sharedPreferences.edit { putString(CHOSEN_PAYLOAD, payload.name) }

    fun getChosen(): Payload = find

    private fun getAllFiles() = getLocation().listFiles().filter { it != null && it.extension == "bin" }.toMutableList()

    private fun isBundledPayload(payload: Payload) = payload.name == BUNDLED_PAYLOAD_SX || payload.name == BUNDLED_PAYLOAD_REINX || payload.name == BUNDLED_PAYLOAD_HEKATE

    private fun isNotBundledPayload(payload: Payload) = payload.name != BUNDLED_PAYLOAD_SX && payload.name != BUNDLED_PAYLOAD_REINX && payload.name != BUNDLED_PAYLOAD_HEKATE

    fun downloadPayload(activity: BaseActivity, name: String, url: String) = GlobalScope.launch(Dispatchers.Main) {
        val properName = if (name.endsWith(".bin")) name else "$name.bin"
        val httpClient = OkHttpClient()

        try {
            withContext(Dispatchers.Default) {
                val request = Request.Builder().url(url).build()

                val response = httpClient.newCall(request).execute().body

                val contentType = response?.contentType()?.subtype

                if (response != null && contentType != null && contentType == "octet-stream") {
                    Logger.info("Downloading payload: $properName.")

                    val targetPlace = File(getLocation(), properName)

                    val sink = targetPlace.sink().buffer()
                    sink.writeAll(response.source())
                    sink.close()

                    response.close()

                    EventBus.getDefault().post(Events.PayloadDownloadedSuccessfully(properName))
                } else {
                    throw Exception()
                }

                withContext(Dispatchers.Main) {
                    Toast.makeText(activity, activity.getString(R.string.payloads_download_status_success, properName), Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(activity, activity.getString(R.string.payloads_download_status_error, properName), Toast.LENGTH_SHORT).show()
            Logger.error("Failed to download payload: $properName.")
        }
    }

}