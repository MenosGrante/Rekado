package com.pavelrekun.rekado.screens.about_activity

import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class AboutActivity : BaseActivity() {

    private lateinit var mvpView: AboutContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        mvpView = AboutView(this)
    }
}