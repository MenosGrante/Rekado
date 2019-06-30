package com.pavelrekun.rekado.screens.payload_fragment

import android.content.Intent

interface PayloadsContract {

    interface View {

        fun initViews()

        fun initList()

        fun initDesign()

        fun initClickListeners()

        fun updateList()

        fun addPayload()

        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)

        fun onResume()

    }

}