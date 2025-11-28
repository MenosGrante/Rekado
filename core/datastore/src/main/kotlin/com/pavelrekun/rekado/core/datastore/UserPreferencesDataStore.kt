package com.pavelrekun.rekado.core.datastore

import androidx.datastore.core.DataStore
import com.pavelrekun.rekado.core.model.userdata.DarkThemeConfig
import com.pavelrekun.rekado.core.model.userdata.UserData
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UserPreferencesDataStore @Inject constructor(
    private val userPreferences: DataStore<UserPreferences>
) {
    val userData = userPreferences.data
        .map {
            UserData(
                darkThemeConfig = DarkThemeConfig.valueOf(it.darkThemeConfig),
                useDynamicColor = it.useDynamicColor
            )
        }

    suspend fun setDynamicColorPreference(useDynamicColor: Boolean) {
        userPreferences.updateData {
            it.copy(useDynamicColor = useDynamicColor)
        }
    }

    suspend fun setDarkThemeConfig(darkThemeConfig: DarkThemeConfig) {
        userPreferences.updateData {
            it.copy(darkThemeConfig = darkThemeConfig.name)
        }
    }
}