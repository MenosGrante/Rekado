package com.pavelrekun.rekado.screens.logs_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.data.Log
import com.pavelrekun.rekado.databinding.ItemLogBinding

class LogsAdapter : ListAdapter<Log, LogsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemLogBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(log: Log) {
            binding.itemLogName.text = log.message

            binding.itemLogType.setBackgroundColor(if (log.type == 0) ContextCompat.getColor(binding.root.context, R.color.colorLogError)
            else ContextCompat.getColor(binding.root.context, R.color.colorLogSuccess))
        }

    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Log> = object : DiffUtil.ItemCallback<Log>() {
            override fun areItemsTheSame(oldItem: Log, newItem: Log): Boolean {
                return oldItem.message == newItem.message
            }

            override fun areContentsTheSame(oldItem: Log, newItem: Log): Boolean {
                return oldItem == newItem
            }
        }

    }

}
