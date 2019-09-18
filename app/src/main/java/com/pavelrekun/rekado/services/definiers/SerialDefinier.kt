package com.pavelrekun.rekado.services.definiers

import com.pavelrekun.rekado.R
import java.util.*

object SerialDefinier {

    private var number: Long = 0

    private const val STATUS_NOT_PATCHED = 0
    private const val STATUS_PATCHED = 1
    private const val STATUS_POSSIBLY_PATCHED = 2
    private const val STATUS_ERROR = 3

    fun defineConsoleStatus(serialNumber: String): Int {
        return when (defineConsoleStatusInternal(serialNumber.toUpperCase(Locale.ROOT))) {
            STATUS_NOT_PATCHED -> R.string.serial_checker_status_not_patched
            STATUS_PATCHED -> R.string.serial_checker_status_patched
            STATUS_POSSIBLY_PATCHED -> R.string.serial_checker_status_possibly_patched
            STATUS_ERROR -> R.string.serial_checker_status_error
            else -> R.string.serial_checker_status_error
        }
    }

    private fun defineConsoleStatusInternal(serialNumber: String): Int {
        this.number = serialNumber.takeLastWhile { !it.isLetter() }.toLong()

        return when (serialNumber.take(4)) {
            "XAW1" -> detectXAW1Status()
            "XAW4" -> detectXAW4Status()
            "XAW7" -> detectXAW7Status()
            "XAJ1" -> detectXAJ1Status()
            "XAJ4" -> detectXAJ4Status()
            "XAJ7" -> detectXAJ7Status()
            "XAW9" -> detectXAW9Status()
            "XAK" -> detectXAKStatus()
            else -> STATUS_ERROR
        }
    }

    private fun detectXAW1Status(): Int {
        return when (number) {
            in 10000000000..10074000000 -> STATUS_NOT_PATCHED
            in 10075000000..10120000000 -> STATUS_POSSIBLY_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAW4Status(): Int {
        return when (number) {
            in 40000000000..40011000000 -> STATUS_NOT_PATCHED
            in 40011000000..40012000000 -> STATUS_POSSIBLY_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAW7Status(): Int {
        return when (number) {
            in 70000000000..70017800000 -> STATUS_NOT_PATCHED
            in 70017800000..70030000000 -> STATUS_POSSIBLY_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAJ1Status(): Int {
        return when (number) {
            in 10000000000..10020000000 -> STATUS_NOT_PATCHED
            in 10020000000..10030000000 -> STATUS_POSSIBLY_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAJ4Status(): Int {
        return when (number) {
            in 40000000000..40046000000 -> STATUS_NOT_PATCHED
            in 40046000000..40060000000 -> STATUS_POSSIBLY_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAJ7Status(): Int {
        return when (number) {
            in 70000000000..70040000000 -> STATUS_NOT_PATCHED
            in 70040000000..70050000000 -> STATUS_POSSIBLY_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAW9Status() = STATUS_POSSIBLY_PATCHED

    private fun detectXAKStatus() = STATUS_POSSIBLY_PATCHED
}