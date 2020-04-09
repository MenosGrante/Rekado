package com.pavelrekun.rekado.screens.payload_fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.penza.services.extensions.EMPTY_STRING
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.databinding.ItemPayloadBinding
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.utils.LoginUtils
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import org.greenrobot.eventbus.EventBus
import java.io.File

class PayloadsAdapter(var data: MutableList<Payload>) : RecyclerView.Adapter<PayloadsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    fun updateList() {
        this.data = PayloadHelper.getAllPayloads()

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemPayloadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemPayloadBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(payload: Payload) {
            binding.itemPayloadName.text = if (payload.version != EMPTY_STRING) "${payload.title} [${payload.version}]" else payload.title

            binding.itemPayloadRemove.visibility = if (PayloadHelper.isBundled(payload.title)) View.GONE else View.VISIBLE

            binding.itemPayloadRemove.setOnClickListener {
                File(payload.getPath()).delete()
                EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
                LoginUtils.info("Payload ${payload.title} deleted!")
            }
        }

    }
}
