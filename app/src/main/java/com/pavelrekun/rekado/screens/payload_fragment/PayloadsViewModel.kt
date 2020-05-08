package com.pavelrekun.rekado.screens.payload_fragment

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Schema
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.extensions.parseSchema
import com.pavelrekun.rekado.services.payloads.PayloadDownloadService
import com.pavelrekun.rekado.services.payloads.Result
import com.pavelrekun.rekado.services.utils.LoginUtils
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PreferencesUtils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PayloadsViewModel : ViewModel() {

    private val service = PayloadDownloadService.createService()

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    val fetchSchemaResult = MutableLiveData<Result>()
    val updatePayloadResult = MutableLiveData<Result>()
    val downloadPayloadResult = MutableLiveData<Result>()

    fun fetchExternalSchema() {
        val errorsHandler = CoroutineExceptionHandler { _, exception ->
            fetchSchemaResult.value = Result.ERROR
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            val response = withContext(Dispatchers.IO) { service.fetchExternalSchema() }

            val body = response.body()
            if (response.isSuccessful && body != null) {
                val schema = body.byteStream().parseSchema()
                val currentSchema = PreferencesUtils.getCurrentSchema()

                if (schema.timestamp > currentSchema.timestamp) {
                    fetchSchemaResult.value = Result.SUCCESS.apply { this.schema = schema }
                }

            } else {
                fetchSchemaResult.value = Result.ERROR
            }

            isProgressShowing.value = false
        }
    }

    fun updatePayloads(updatedSchema: Schema) {
        val errorsHandler = CoroutineExceptionHandler { _, _ ->
            updatePayloadResult.value = Result.ERROR
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            isProgressShowing.value = true

            val currentSchema = PreferencesUtils.getCurrentSchema().payloads

            updatedSchema.payloads
                    .filterIndexed { index, payload -> payload.version != currentSchema[index].version }
                    .forEach {
                        val response = withContext(Dispatchers.IO) { service.downloadPayload(it.downloadUrl) }
                        val body = response.body()

                        if (response.isSuccessful && body != null) {
                            MemoryUtils.copyPayload(body.byteStream(), it.title)
                        }
                    }.apply {
                        PreferencesUtils.saveSchema(updatedSchema)
                    }

            updatePayloadResult.value = Result.SUCCESS
            isProgressShowing.value = false
        }
    }

    fun downloadPayload(name: String, url: String) {
        val errorsHandler = CoroutineExceptionHandler { _, _ ->
            downloadPayloadResult.value = Result.ERROR
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            isProgressShowing.value = true

            val finalName = if (name.endsWith(".bin")) name else "$name.bin"

            if (!url.contains("https") || !url.contains("http")) {
                downloadPayloadResult.value = Result.ERROR
            }

            val response = withContext(Dispatchers.IO) { service.downloadPayload(url) }
            val body = response.body()
            val contentType = body?.contentType()?.subtype()

            if (response.isSuccessful && body != null && contentType != null && Constants.Mimes.BINARY.contains(contentType)) {
                LoginUtils.info("Downloading payload: $finalName.")
                withContext(Dispatchers.IO) { MemoryUtils.copyPayload(body.byteStream(), finalName) }

                downloadPayloadResult.value = Result.SUCCESS
            } else {
                downloadPayloadResult.value = Result.ERROR
            }

            isProgressShowing.value = false
        }

    }

}