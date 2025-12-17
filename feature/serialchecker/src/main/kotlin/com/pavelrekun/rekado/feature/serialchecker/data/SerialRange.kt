package com.pavelrekun.rekado.feature.serialchecker.data

internal data class SerialRange(
    val notPatched: LongRange? = null,
    val possiblyPatched: LongRange? = null,
    val alwaysPatched: Boolean = false
) {
    fun checkStatus(number: Long): SerialStatus = when {
        alwaysPatched -> SerialStatus.Patched
        notPatched?.contains(number) == true -> SerialStatus.NotPatched
        possiblyPatched?.contains(number) == true -> SerialStatus.PossiblyPatched
        notPatched != null || possiblyPatched != null -> SerialStatus.Patched
        else -> SerialStatus.PossiblyPatched
    }

    companion object {
        fun possiblyPatchedOnly() = SerialRange()
        fun patchedOnly() = SerialRange(alwaysPatched = true)
    }
}