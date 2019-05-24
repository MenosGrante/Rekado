package com.pavelrekun.rekado.screens.serial_checker_activity

import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class SerialCheckerActivity : BaseActivity() {

    private lateinit var mvpView: SerialCheckerContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_serial_checker)

        mvpView = SerialCheckerView(this)
    }
}