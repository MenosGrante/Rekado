package com.pavelrekun.rekado.screens.logs_fragment.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Log
import com.pavelrekun.rekado.services.Logger
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_log.*

class LogsAdapter(var data: MutableList<Log>) : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    fun updateList() {
        this.data = Logger.getLogs()

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

            itemLogRoot.strokeColor = if (log.type == 0) ContextCompat.getColor(RekadoApplication.context, R.color.colorRed)
            else ContextCompat.getColor(RekadoApplication.context, R.color.colorGreen)
        }

    }
}
