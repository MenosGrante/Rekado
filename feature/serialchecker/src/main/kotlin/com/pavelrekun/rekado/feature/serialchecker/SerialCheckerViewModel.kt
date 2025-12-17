package com.pavelrekun.rekado.feature.serialchecker

import androidx.lifecycle.ViewModel
import com.journeyapps.barcodescanner.ScanIntentResult
import com.pavelrekun.rekado.feature.serialchecker.data.SerialRange
import com.pavelrekun.rekado.feature.serialchecker.data.SerialRange.Companion.patchedOnly
import com.pavelrekun.rekado.feature.serialchecker.data.SerialRange.Companion.possiblyPatchedOnly
import com.pavelrekun.rekado.feature.serialchecker.data.SerialStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class SerialCheckerViewModel @Inject constructor() : ViewModel() {

    private val _state = MutableStateFlow(SerialCheckerState())
    val state: StateFlow<SerialCheckerState> = _state.asStateFlow()

    fun onSerialNumberChanged(serialNumber: String) {
        val status = checkSerialStatus(serialNumber)
        _state.update { it.copy(serialNumber = serialNumber, status = status) }
    }

    private fun checkSerialStatus(serialNumber: String): SerialStatus {
        if (serialNumber.isBlank()) return SerialStatus.Empty

        val normalized = serialNumber.uppercase(Locale.ROOT)
        val prefix = normalized.take(4)
        val number = normalized.takeLastWhile { it.isDigit() }.toLongOrNull()
            ?: return SerialStatus.Empty

        return SERIAL_RANGES[prefix]?.checkStatus(number) ?: SerialStatus.UnknownPrefix
    }

    fun onScanResult(scanResult: ScanIntentResult?) = scanResult?.contents?.let { serial ->
        onSerialNumberChanged(serial)
    }

    companion object {
        private val SERIAL_RANGES = mapOf(
            "XAW1" to SerialRange(
                notPatched = 10000000000L..10074000000L,
                possiblyPatched = 10074000000L..10120000000L
            ),
            "XAW4" to SerialRange(
                notPatched = 40000000000L..40011000000L,
                possiblyPatched = 40011000000L..40012000000L
            ),
            "XAW7" to SerialRange(
                notPatched = 70000000000L..70017800000L,
                possiblyPatched = 70017800000L..70030000000L
            ),
            "XAJ1" to SerialRange(
                notPatched = 10000000000L..10020000000L,
                possiblyPatched = 10020000000L..10030000000L
            ),
            "XAJ4" to SerialRange(
                notPatched = 40000000000L..40046000000L,
                possiblyPatched = 40046000000L..40060000000L
            ),
            "XAJ7" to SerialRange(
                notPatched = 70000000000L..70040000000L,
                possiblyPatched = 70040000000L..70050000000L
            ),
            "XAW9" to possiblyPatchedOnly(),
            "XAK" to possiblyPatchedOnly(),

            // New prefixes - all patched
            "XKW1" to patchedOnly(),
            "XKJ1" to patchedOnly(),
            "XJW1" to patchedOnly(),
            "XWW1" to patchedOnly()
        )
    }
}