package com.pavelrekun.rekado.screens.translators_activity

import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class TranslatorsActivity : BaseActivity() {

    private lateinit var mvpView: TranslatorsContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_translators)

        mvpView = TranslatorsView(this)
    }
}