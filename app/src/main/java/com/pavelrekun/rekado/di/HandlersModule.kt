package com.pavelrekun.rekado.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.pavelrekun.rekado.services.handlers.PayloadsHandler
import com.pavelrekun.rekado.services.handlers.PreferencesHandler
import com.pavelrekun.rekado.services.handlers.SerialNumberHandler
import com.pavelrekun.rekado.services.handlers.StorageHandler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HandlersModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    @Provides
    fun providePreferencesHandler(sharedPreferences: SharedPreferences): PreferencesHandler {
        return PreferencesHandler(sharedPreferences)
    }

    @Provides
    @Singleton
    fun provideStorageHandler(@ApplicationContext context: Context,
                              preferencesHandler: PreferencesHandler): StorageHandler {
        return StorageHandler(context, preferencesHandler)
    }

    @Provides
    @Singleton
    fun providePayloadsHandler(storageHandler: StorageHandler,
                               preferencesHandler: PreferencesHandler): PayloadsHandler {
        return PayloadsHandler(preferencesHandler, storageHandler)
    }

    @Provides
    @Singleton
    fun provideSerialNumberHandler(): SerialNumberHandler {
        return SerialNumberHandler()
    }

}