package com.pavelrekun.rekado.screens.payload_fragment

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrekun.rekado.data.Config
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.extensions.parseConfig
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

    val fetchConfigResult = MutableLiveData<Result>()
    val updatePayloadResult = MutableLiveData<Result>()
    val downloadPayloadResult = MutableLiveData<Result>()

    fun fetchExternalConfig() {
        val errorsHandler = CoroutineExceptionHandler { _, exception ->
            fetchConfigResult.value = Result.ERROR
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            val response = withContext(Dispatchers.IO) { service.fetchExternalConfig() }

            val body = response.body()
            if (response.isSuccessful && body != null) {
                val config = body.byteStream().parseConfig()
                val currentConfig = PreferencesUtils.getCurrentConfig()

                if (config.timestamp > currentConfig.timestamp) {
                    fetchConfigResult.value = Result.SUCCESS.apply { this.config = config }
                }

            } else {
                fetchConfigResult.value = Result.ERROR
            }

            isProgressShowing.value = false
        }
    }

    fun updatePayloads(updatedConfig: Config) {
        val errorsHandler = CoroutineExceptionHandler { _, _ ->
            updatePayloadResult.value = Result.ERROR
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            isProgressShowing.value = true

            val currentConfig = PreferencesUtils.getCurrentConfig().payloads

            updatedConfig.payloads
                    .filterIndexed { index, payload -> payload.version != currentConfig[index].version }
                    .forEach {
                        val response = withContext(Dispatchers.IO) { service.downloadPayload(it.downloadUrl) }
                        val body = response.body()

                        if (response.isSuccessful && body != null) {
                            MemoryUtils.copyPayload(body.byteStream(), it.title)
                        }
                    }.apply {
                        PreferencesUtils.saveConfig(updatedConfig)
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