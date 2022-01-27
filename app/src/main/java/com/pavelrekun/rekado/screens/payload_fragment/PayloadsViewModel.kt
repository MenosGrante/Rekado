package com.pavelrekun.rekado.screens.payload_fragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pavelrekun.rekado.api.PayloadDownloadAPI
import com.pavelrekun.rekado.base.BaseSingleEventLiveData
import com.pavelrekun.rekado.data.Config
import com.pavelrekun.rekado.data.base.ResultWrapper
import com.pavelrekun.rekado.services.constants.Mimes
import com.pavelrekun.rekado.services.extensions.safeApiCall
import com.pavelrekun.rekado.services.handlers.PreferencesHandler
import com.pavelrekun.rekado.services.handlers.StorageHandler
import com.pavelrekun.rekado.services.utils.LoginUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PayloadsViewModel @Inject constructor(private val payloadDownloadService: PayloadDownloadAPI,
                                            private val preferencesHandler: PreferencesHandler,
                                            private val storageHandler: StorageHandler) : ViewModel() {

    val isProgressShowing: BaseSingleEventLiveData<Boolean> by lazy { BaseSingleEventLiveData() }

    val configFetchSuccess: BaseSingleEventLiveData<Config> by lazy { BaseSingleEventLiveData() }
    val downloadSuccess: BaseSingleEventLiveData<String> by lazy { BaseSingleEventLiveData() }

    val errorResult: BaseSingleEventLiveData<Throwable> by lazy { BaseSingleEventLiveData() }
    val updateList: BaseSingleEventLiveData<Unit> by lazy { BaseSingleEventLiveData() }

    fun fetchExternalConfig() {
        val errorsHandler = CoroutineExceptionHandler { _, exception ->
            errorResult.postValue(exception)
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            isProgressShowing.value = true

            when (val result = safeApiCall(Dispatchers.IO) { payloadDownloadService.fetchExternalConfig() }) {
                is ResultWrapper.Error -> errorResult.postValue(result.error)
                is ResultWrapper.Success -> configFetchSuccess.postValue(result.value.body())
            }

            isProgressShowing.value = false
        }
    }

    fun updatePayloads(newConfig: Config, currentConfig: Config) {
        val errorsHandler = CoroutineExceptionHandler { _, exception ->
            errorResult.postValue(exception)
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            isProgressShowing.value = true

            currentConfig.payloads
                    .filter { payload ->
                        val foundPayload = newConfig.payloads.find { it.title == payload.title }
                                ?: return@filter false
                        payload.version != foundPayload.version
                    }
                    .forEach {
                        val downloadPayloadResult = safeApiCall(Dispatchers.IO) { payloadDownloadService.downloadPayload(it.downloadUrl!!) }

                        if (downloadPayloadResult is ResultWrapper.Success) {
                            val resultInputStream = downloadPayloadResult.value.body()?.byteStream()

                            if (resultInputStream != null) {
                                storageHandler.copyPayload(resultInputStream, it.title)
                            } else {
                                errorResult.postValue(createGenericError("Error during downloading payload!"))
                            }
                        } else if(downloadPayloadResult is ResultWrapper.Error) {
                            errorResult.postValue(downloadPayloadResult.error)
                        }
                    }.apply {
                        preferencesHandler.saveConfig(newConfig)
                        updatePayloads()
                    }

            isProgressShowing.value = false
        }
    }

    fun downloadPayload(name: String, url: String) {
        val errorsHandler = CoroutineExceptionHandler { _, exception ->
            errorResult.postValue(exception)
            isProgressShowing.value = false
        }

        viewModelScope.launch(Dispatchers.Main + errorsHandler) {
            isProgressShowing.value = true

            val finalName = if (name.endsWith(".bin")) name else "$name.bin"

            if (!url.contains("https") || !url.contains("http")) {
                errorResult.postValue(createGenericError("Incorrect download URL!"))
            }

            val result = safeApiCall(Dispatchers.IO) { payloadDownloadService.downloadPayload(url) }

            if (result is ResultWrapper.Success) {
                val body = result.value.body()
                val contentType = body?.contentType()?.subtype

                if (body != null && contentType != null && Mimes.BINARY.contains(contentType)) {
                    LoginUtils.info("Downloading payload: $finalName.")
                    storageHandler.copyPayload(body.byteStream(), finalName)

                    downloadSuccess.postValue(finalName)

                    updatePayloads()
                } else {
                    errorResult.postValue(createGenericError("Incorrect content type: ${contentType}!"))
                }
            } else if (result is ResultWrapper.Error) {
                errorResult.postValue(result.error)
            }

            isProgressShowing.value = false
        }

    }

    fun updatePayloads() {
        updateList.postValue(Unit)
    }

    private fun createGenericError(message: String? = null): Exception {
        return Exception(message)
    }

}