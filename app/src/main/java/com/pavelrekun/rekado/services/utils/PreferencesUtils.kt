package com.pavelrekun.rekado.services.utils

import androidx.core.content.edit
import androidx.preference.PreferenceManager
import com.google.gson.GsonBuilder
import com.pavelrekun.penza.services.extensions.EMPTY_STRING
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.data.Schema
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper.find

object PreferencesUtils {

    private val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.context)

    private const val AUTO_INJECTOR_ENABLED = "auto_injector_enable"
    private const val AUTO_INJECTOR_PAYLOAD = "auto_injector_payload"

    private const val CURRENT_SCHEMA = "CURRENT_SCHEMA"

    private const val CHOSEN_PAYLOAD = "CHOSEN_PAYLOAD"

    fun checkAutoInjectorEnabled() = sharedPreferences.getBoolean(AUTO_INJECTOR_ENABLED, false)

    fun getAutoInjectorPayload() = sharedPreferences.getString(AUTO_INJECTOR_PAYLOAD, PayloadHelper.BUNDLED_PAYLOADS.first())

    fun saveSchema(schema: Schema) {
        sharedPreferences.edit {
            val jsonSchema = GsonBuilder().create().toJson(schema)
            putString(CURRENT_SCHEMA, jsonSchema)
        }
    }

    fun getCurrentSchema(): Schema {
        val savedSchema = sharedPreferences.getString(CURRENT_SCHEMA, EMPTY_STRING)
        return GsonBuilder().create().fromJson(savedSchema, Schema::class.java)
    }

    fun checkSchemaExists() = sharedPreferences.contains(CURRENT_SCHEMA)

    fun putChosen(payload: Payload) = sharedPreferences.edit { putString(CHOSEN_PAYLOAD, payload.title) }

    fun getChosen(): Payload = find(sharedPreferences.getString(CHOSEN_PAYLOAD, EMPTY_STRING) as String)

}