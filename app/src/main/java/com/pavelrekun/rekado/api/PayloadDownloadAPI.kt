package com.pavelrekun.rekado.api


import com.pavelrekun.rekado.data.Config
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface PayloadDownloadAPI {

    @Streaming
    @GET
    suspend fun downloadPayload(@Url downloadUrl: String): Response<ResponseBody>

    @GET("/payloads/config.json")
    suspend fun fetchExternalConfig(): Response<Config>

}