package com.pavelrekun.rekado.screens.logs_fragment

import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.penza.services.extensions.tintContrast
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.logs_fragment.adapters.LogsAdapter
import com.pavelrekun.rekado.services.Logger
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
        adapter = LogsAdapter(Logger.getLogs())

        activity.logsActionsList.setHasFixedSize(true)
        activity.logsActionsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        activity.logsActionsList.adapter = adapter

        activity.logsActionsList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) activity.logsClearButton.hide()
                else if (dy < 0) activity.logsClearButton.show()
            }
        })
    }

    override fun initClickListeners() {
        activity.logsClearButton.setOnClickListener {
            Logger.clearLogs()

            if (this::adapter.isInitialized) {
                adapter.updateList()
            }
        }
    }

    override fun initDesign() {
        activity.logsClearButton.tintContrast()
    }

    override fun onResume() {
        if (this::adapter.isInitialized) {
            adapter.updateList()
        }
    }
}