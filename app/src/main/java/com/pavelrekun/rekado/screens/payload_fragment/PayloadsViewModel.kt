package com.pavelrekun.rekado.screens.payload_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrekun.rekado.data.Schema
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.extensions.parseSchema
import com.pavelrekun.rekado.services.payloads.PayloadDownloadHelper
import com.pavelrekun.rekado.services.utils.LoginUtils
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PreferencesUtils
import com.pavelrekun.rekado.services.utils.Utils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PayloadsViewModel : ViewModel() {

    val isProgressShowing: MutableLiveData<Boolean> = MutableLiveData()

    val fetchSchemaResult = MutableLiveData<PayloadDownloadHelper.Result>()
    val updatePayloadResult = MutableLiveData<PayloadDownloadHelper.Result>()
    val downloadPayloadResult = MutableLiveData<PayloadDownloadHelper.Result>()

    fun fetchExternalSchema() {
        if (!Utils.isOnline()) {
            fetchSchemaResult.value = PayloadDownloadHelper.Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                val response = withContext(Dispatchers.IO) { PayloadDownloadHelper.fetchExternalSchema() }

                val body = response.body
                if (response.isSuccessful && body != null) {
                    val schema = body.byteStream().parseSchema()
                    val currentSchema = PreferencesUtils.getCurrentSchema()

                    if (schema.timestamp > currentSchema.timestamp) {
                        fetchSchemaResult.value = PayloadDownloadHelper.Result.SUCCESS.apply { this.schema = schema }
                    }

                } else {
                    fetchSchemaResult.value = PayloadDownloadHelper.Result.ERROR
                }

                isProgressShowing.value = false
            }
        }
    }

    fun updatePayloads(updatedSchema: Schema) {
        if (!Utils.isOnline()) {
            updatePayloadResult.value = PayloadDownloadHelper.Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val currentSchema = PreferencesUtils.getCurrentSchema().payloads

                updatedSchema.payloads
                        .filterIndexed { index, payload -> payload.version != currentSchema[index].version }
                        .forEach {
                            val response = withContext(Dispatchers.IO) { PayloadDownloadHelper.downloadPayload(it.downloadUrl) }
                            val body = response.body

                            if (response.isSuccessful && body != null) {
                                MemoryUtils.copyPayload(body.byteStream(), it.title)
                            }
                        }.apply {
                            PreferencesUtils.saveSchema(updatedSchema)
                        }

                updatePayloadResult.value = PayloadDownloadHelper.Result.SUCCESS
                isProgressShowing.value = false
            }
        }
    }

    fun downloadPayload(name: String, url: String) {
        if (!Utils.isOnline()) {
            updatePayloadResult.value = PayloadDownloadHelper.Result.ERROR
        } else {
            viewModelScope.launch(Dispatchers.Main) {
                isProgressShowing.value = true

                val finalName = if (name.endsWith(".bin")) name else "$name.bin"

                if (!url.contains("https") || !url.contains("http")) {
                    downloadPayloadResult.value = PayloadDownloadHelper.Result.ERROR
                }

                val response = withContext(Dispatchers.IO) { PayloadDownloadHelper.downloadPayload(url) }
                val body = response.body
                val contentType = body?.contentType()?.subtype

                if (response.isSuccessful && body != null && contentType != null && Constants.Mimes.BINARY.contains(contentType)) {
                    LoginUtils.info("Downloading payload: $finalName.")
                    withContext(Dispatchers.IO) { MemoryUtils.copyPayload(body.byteStream(), finalName) }

                    downloadPayloadResult.value = PayloadDownloadHelper.Result.SUCCESS
                } else {
                    downloadPayloadResult.value = PayloadDownloadHelper.Result.ERROR
                }

                isProgressShowing.value = false
            }
        }

    }

}