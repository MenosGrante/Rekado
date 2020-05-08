package com.pavelrekun.rekado.services.payloads

import com.pavelrekun.rekado.services.Constants.BASE_URL_SCHEMA
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object PayloadDownloadService {

    fun createService(): PayloadDownloadAPI {
        val retrofit = Retrofit.Builder()
                .client(OkHttpClient())
                .baseUrl(BASE_URL_SCHEMA)
                .build()

        return retrofit.create(PayloadDownloadAPI::class.java)
    }

}