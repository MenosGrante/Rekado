package com.pavelrekun.rekado.screens.serial_checker_activity

import android.content.Intent

interface SerialCheckerContract {

    interface View {

        fun onViewCreated()

        fun initToolbar()

        fun initClickListeners()

        fun generateInformation()

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

    }

}