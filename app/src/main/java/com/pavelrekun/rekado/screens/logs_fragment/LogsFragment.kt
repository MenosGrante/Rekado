package com.pavelrekun.rekado.screens.logs_fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentLogsBinding
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.LoginUtils

class LogsFragment : BaseFragment(R.layout.fragment_logs) {

    private lateinit var adapter: LogsAdapter

    private val binding by viewBinding(FragmentLogsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initList()
        initClickListeners()
    }

    override fun onResume() {
        super.onResume()

        if (this::adapter.isInitialized) {
            adapter.submitList(LoginUtils.getLogs())
        }
    }

    private fun initList() {
        adapter = LogsAdapter()
        adapter.submitList(LoginUtils.getLogs())

        binding.logsActionsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireBaseActivity())
            adapter = this@LogsFragment.adapter

            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    if (dy > 0) binding.logsClearButton.hide()
                    else if (dy < 0) binding.logsClearButton.show()
                }
            })
        }
    }

    private fun initClickListeners() {
        binding.logsClearButton.setOnClickListener {
            LoginUtils.clearLogs()

            if (this::adapter.isInitialized) {
                adapter.submitList(LoginUtils.getLogs())
            }
        }
    }

}