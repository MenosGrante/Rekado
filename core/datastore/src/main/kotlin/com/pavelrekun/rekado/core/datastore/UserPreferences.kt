package com.pavelrekun.rekado.core.datastore

import com.pavelrekun.rekado.core.model.userdata.DarkThemeConfig.FOLLOW_SYSTEM
import kotlinx.serialization.Serializable

@Serializable
data class UserPreferences(
    val darkThemeConfig: String = FOLLOW_SYSTEM.name,
    val useDynamicColor: Boolean = true
)