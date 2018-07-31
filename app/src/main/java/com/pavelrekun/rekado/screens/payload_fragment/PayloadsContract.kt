package com.pavelrekun.rekado.screens.payload_fragment

interface PayloadsContract {

    interface View {

        fun initViews()

        fun prepareList()

        fun initList()

        fun initDesign()

        fun updateList()

        fun initClickListeners()

        fun addPayload()

        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)

    }

}