package com.pavelrekun.rekado.screens.logs_fragment

import android.support.v7.widget.LinearLayoutManager
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.logs_fragment.adapters.LogsAdapter
import com.pavelrekun.rekado.services.logs.Logger
import kotlinx.android.synthetic.main.fragment_logs.*

class LogsView(private val activity: BaseActivity) : LogsContract.View {

    private lateinit var adapter: LogsAdapter

    override fun initViews() {
        activity.setTitle(R.string.navigation_logs)

        initList()
    }

    override fun initList() {
        adapter = LogsAdapter(Logger.getLogs())

        activity.logsActionsList.setHasFixedSize(true)
        activity.logsActionsList.layoutManager = LinearLayoutManager(activity)
        activity.logsActionsList.adapter = adapter
    }
}