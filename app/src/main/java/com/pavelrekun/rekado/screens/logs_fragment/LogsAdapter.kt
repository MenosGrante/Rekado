package com.pavelrekun.rekado.screens.logs_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.data.Log
import com.pavelrekun.rekado.databinding.ItemLogBinding
import com.pavelrekun.rekado.services.utils.LoginUtils

class LogsAdapter(var data: MutableList<Log>) : RecyclerView.Adapter<LogsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    fun updateList() {
        this.data = LoginUtils.getLogs()

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemLogBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(log: Log) {
            binding.itemLogName.text = log.message
            binding.itemLogRoot.strokeColor = if (log.type == 0) ContextCompat.getColor(RekadoApplication.context, R.color.colorRed)
            else ContextCompat.getColor(RekadoApplication.context, R.color.colorGreen)
        }

    }
}
