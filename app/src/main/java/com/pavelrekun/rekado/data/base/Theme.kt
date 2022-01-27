package com.pavelrekun.rekado.data.base

enum class Theme {

    LIGHT, DARK, SYSTEM_DEFAULT;

    companion object {

        fun find(index: Int) = values()[index]

    }

}