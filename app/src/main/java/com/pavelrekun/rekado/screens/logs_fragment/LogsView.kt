package com.pavelrekun.rekado.screens.logs_fragment

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.pavelrekun.rang.utils.ColorsHelper
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.logs_fragment.adapters.LogsAdapter
import com.pavelrekun.rekado.services.logs.LogHelper
import kotlinx.android.synthetic.main.fragment_logs.*

class LogsView(private val activity: BaseActivity) : LogsContract.View {

    private lateinit var adapter: LogsAdapter

    override fun initViews() {
        activity.setTitle(R.string.navigation_logs)

        initList()
        initClickListeners()
        initDesign()
    }

    override fun initList() {
        adapter = LogsAdapter(LogHelper.getLogs())

        activity.logsActionsList.setHasFixedSize(true)
        activity.logsActionsList.layoutManager = LinearLayoutManager(activity)
        activity.logsActionsList.adapter = adapter

        activity.logsActionsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                if (dy > 0) activity.logsClearButton.hide()
                else if (dy < 0) activity.logsClearButton.show()
            }
        })
    }

    override fun initClickListeners() {
        activity.logsClearButton.setOnClickListener {
            LogHelper.clearLogs()

            if (this::adapter.isInitialized) {
                adapter.updateList()
            }
        }
    }

    override fun initDesign() {
        activity.logsClearButton.setColorFilter(ColorsHelper.getContrastColor(activity, ColorsHelper.resolveAccentColor(activity)))
    }
}