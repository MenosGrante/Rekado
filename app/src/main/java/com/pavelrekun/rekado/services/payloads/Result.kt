package com.pavelrekun.rekado.services.payloads

import com.pavelrekun.rekado.data.Schema
import java.lang.Exception

enum class Result {
    SUCCESS, ERROR;

    lateinit var schema: Schema
    lateinit var exception: Exception
}