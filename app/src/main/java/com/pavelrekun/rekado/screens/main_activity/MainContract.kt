package com.pavelrekun.rekado.screens.main_activity

import android.content.Intent

interface MainContract {

    interface View {

        fun initViews()

        fun initToolbar()

        fun initNavigationClickListener()

        fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?)

    }

}