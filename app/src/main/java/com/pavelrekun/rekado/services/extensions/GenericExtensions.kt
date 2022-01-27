package com.pavelrekun.rekado.services.extensions

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.net.Uri
import android.provider.MediaStore
import androidx.annotation.RawRes
import com.pavelrekun.rekado.data.Config
import com.pavelrekun.rekado.data.ConfigJsonAdapter
import com.pavelrekun.rekado.data.base.ResultWrapper
import com.squareup.moshi.Moshi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.File
import java.io.InputStream

fun InputStream.toFile(path: String) {
    File(path).outputStream().use { this.copyTo(it) }
}

fun String.toConfig(): Config? {
    val moshi = Moshi.Builder().build()
    val adapter = ConfigJsonAdapter(moshi)
    return adapter.fromJson(this)
}

fun Config.toJson(): String {
    val moshi = Moshi.Builder().build()
    val adapter = ConfigJsonAdapter(moshi)
    return adapter.toJson(this)
}

fun Resources.readConfig(@RawRes configId: Int): Config {
    val configJson = openRawResource(configId).bufferedReader().use { it.readText() }
    return configJson.toConfig() as Config // Bundled config always present and won't be null
}

/**
 * Extracts file name from the given [Uri].
 *
 * @param context - context, that is used to get content resolver.
 *
 * @return - file name if [Uri] is associated with the file, null otherwise.
 */
fun Uri.extractFileName(context: Context): String? {
    val projection = arrayOf(MediaStore.MediaColumns.DISPLAY_NAME)
    context.contentResolver.query(this, projection, null, null, null)?.use {
        if (it.moveToFirst()) {
            return it.getString(0)
        }
    }

    return null
}

/**
 * Recreate current activity to apply configuration changes.
 */
fun Activity.restartApp() {
    this.recreate()
}

suspend fun <T> safeApiCall(dispatcher: CoroutineDispatcher, apiCall: suspend () -> T): ResultWrapper<T> {
    return withContext(dispatcher) {
        try {
            ResultWrapper.Success(apiCall.invoke())
        } catch (throwable: Throwable) {
            if(throwable is HttpException) {
                ResultWrapper.Error(throwable.code(), throwable)
            } else {
                ResultWrapper.Error(null, throwable)
            }
        }
    }
}