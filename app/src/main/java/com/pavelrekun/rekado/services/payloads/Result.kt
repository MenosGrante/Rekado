package com.pavelrekun.rekado.services.payloads

import com.pavelrekun.rekado.data.Config
import java.lang.Exception

enum class Result {
    SUCCESS, ERROR;

    lateinit var config: Config
}