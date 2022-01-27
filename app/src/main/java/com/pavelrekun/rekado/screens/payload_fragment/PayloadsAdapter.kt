package com.pavelrekun.rekado.screens.payload_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.databinding.ItemPayloadBinding
import com.pavelrekun.rekado.services.handlers.PayloadsHandler

class PayloadsAdapter(private val payloadsHandler: PayloadsHandler,
                      private val removeClickListener: (Payload) -> Unit)
    : ListAdapter<Payload, PayloadsAdapter.ViewHolder>(DIFF_CALLBACK) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPayloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val viewHolder = ViewHolder(binding, payloadsHandler)

        binding.itemPayloadRemove.setOnClickListener {
            val payloadToRemove = getItem(viewHolder.bindingAdapterPosition)
            removeClickListener.invoke(payloadToRemove)
        }

        return viewHolder
    }

    class ViewHolder(private val binding: ItemPayloadBinding,
                     private val payloadsHandler: PayloadsHandler) : RecyclerView.ViewHolder(binding.root) {

        fun bind(payload: Payload) {
            binding.itemPayloadName.text = if (!payload.version.isNullOrEmpty()) "${payload.title} [${payload.version}]" else payload.title
            binding.itemPayloadRemove.visibility = if (payloadsHandler.isBundled(payload.title)) View.GONE else View.VISIBLE
        }

    }

    companion object {

        val DIFF_CALLBACK: DiffUtil.ItemCallback<Payload> = object : DiffUtil.ItemCallback<Payload>() {
            override fun areItemsTheSame(oldItem: Payload, newItem: Payload): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Payload, newItem: Payload): Boolean {
                return oldItem == newItem
            }
        }

    }
}
