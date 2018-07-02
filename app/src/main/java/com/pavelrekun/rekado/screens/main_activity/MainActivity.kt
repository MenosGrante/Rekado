package com.pavelrekun.rekado.screens.main_activity

import android.content.Intent
import android.os.Bundle
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import org.greenrobot.eventbus.EventBus


class MainActivity : BaseActivity() {

    private lateinit var mvpView: MainContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpView = MainView(this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        mvpView.onActivityResult(requestCode, resultCode, resultData)
    }
}
