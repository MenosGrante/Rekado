package com.pavelrekun.rekado.screens.main_activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity


class MainActivity : BaseActivity() {

    private lateinit var mvpView: MainContract.View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mvpView = MainView(this, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return mvpView.onOptionsItemSelected(item)
    }

}