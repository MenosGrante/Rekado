package com.pavelrekun.rekado.core.data.repository

import com.pavelrekun.rekado.core.datastore.UserPreferencesDataStore
import com.pavelrekun.rekado.core.model.userdata.DarkThemeConfig
import com.pavelrekun.rekado.core.model.userdata.UserData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class UserDataRepository @Inject constructor(
    private val userPreferencesDataStore: UserPreferencesDataStore
) {
    val userData: Flow<UserData> = userPreferencesDataStore.userData

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferencesDataStore.setDarkThemeConfig(darkThemeConfig)
    }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferencesDataStore.setDynamicColorPreference(useDynamicColor)
    }
}