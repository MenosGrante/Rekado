package com.pavelrekun.rekado.services.payloads

import com.pavelrekun.rekado.services.Constants.BASE_URL_CONFIG
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object PayloadDownloadService {

    fun createService(): PayloadDownloadAPI {
        val retrofit = Retrofit.Builder()
                .client(OkHttpClient())
                .baseUrl(BASE_URL_CONFIG)
                .build()

        return retrofit.create(PayloadDownloadAPI::class.java)
    }

}