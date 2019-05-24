package com.pavelrekun.rekado.screens.serial_checker_activity

interface SerialCheckerContract {

    interface View {

        fun onViewCreated()

        fun initToolbar()

        fun initClickListeners()

        fun generateInformation()

    }

}