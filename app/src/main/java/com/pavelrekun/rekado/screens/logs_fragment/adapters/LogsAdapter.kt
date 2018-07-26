package com.pavelrekun.rekado.screens.logs_fragment.adapters

import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Log
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.logs.LogHelper.ERROR
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_log.*

class LogsAdapter(var data: MutableList<Log>) : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    fun updateList() {
        this.data = LogHelper.getLogs()

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_log, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(log: Log) {
            itemLogName.text = log.message

            itemLogType.setBackgroundColor(if(log.type == ERROR) ContextCompat.getColor(RekadoApplication.instance.applicationContext, R.color.colorRed)
            else ContextCompat.getColor(RekadoApplication.instance.applicationContext, R.color.colorGreen))
        }

    }
}
