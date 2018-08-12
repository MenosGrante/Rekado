package com.pavelrekun.rekado.screens.main_activity

import android.view.MenuItem

interface MainContract {

    interface View {

        fun initViews()

        fun initToolbar()

        fun initNavigationClickListener()

        fun onStart()

        fun onStop()

        fun onOptionsItemSelected(item: MenuItem): Boolean
    }

}