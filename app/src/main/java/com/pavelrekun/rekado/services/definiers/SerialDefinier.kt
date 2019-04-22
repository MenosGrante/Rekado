package com.pavelrekun.rekado.services.definiers

object SerialDefinier {

    private var number: Long = 0

    const val STATUS_NOT_PATCHED = 0
    const val STATUS_PATCHED = 1
    const val STATUS_MAY_BE_PATCHED = 2
    const val STATUS_UNDEFINED = 3

    fun defineConsoleStatus(serialNumber: String): Int {
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
            else -> STATUS_UNDEFINED
        }
    }

    private fun detectXAW1Status(): Int {
        return when (number) {
            in 10000000000..10074000000 -> STATUS_NOT_PATCHED
            in 10075000000..10120000000 -> STATUS_MAY_BE_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAW4Status(): Int {
        return when (number) {
            in 40000000000..40011000000 -> STATUS_NOT_PATCHED
            in 40011000000..40012000000 -> STATUS_MAY_BE_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAW7Status(): Int {
        return when (number) {
            in 70000000000..70017800000 -> STATUS_NOT_PATCHED
            in 70017800000..70030000000 -> STATUS_MAY_BE_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAJ1Status(): Int {
        return when (number) {
            in 10000000000..10020000000 -> STATUS_NOT_PATCHED
            in 10020000000..10030000000 -> STATUS_MAY_BE_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAJ4Status(): Int {
        return when (number) {
            in 40000000000..40046000000 -> STATUS_NOT_PATCHED
            in 40046000000..40060000000 -> STATUS_MAY_BE_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAJ7Status(): Int {
        return when (number) {
            in 70000000000..70040000000 -> STATUS_NOT_PATCHED
            in 70040000000..70050000000 -> STATUS_MAY_BE_PATCHED
            else -> STATUS_PATCHED
        }
    }

    private fun detectXAW9Status() = STATUS_MAY_BE_PATCHED

    private fun detectXAKStatus() = STATUS_MAY_BE_PATCHED
}