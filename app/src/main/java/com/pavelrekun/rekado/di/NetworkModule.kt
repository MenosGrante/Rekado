package com.pavelrekun.rekado.di

import com.pavelrekun.rekado.api.PayloadDownloadAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideBaseUrl() = "https://pavelrekun.dev"

    @Provides
    @Singleton
    fun provideRetrofit(baseUrl: String): Retrofit {
        val logging = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
        val okHttpClient = OkHttpClient.Builder().addInterceptor(logging).build()

        return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(baseUrl)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
    }

    @Provides
    @Singleton
    fun providePayloadsDownloadService(retrofit: Retrofit) = retrofit.create(PayloadDownloadAPI::class.java)

}