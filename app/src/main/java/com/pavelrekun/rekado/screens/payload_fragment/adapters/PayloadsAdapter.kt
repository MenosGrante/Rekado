package com.pavelrekun.rekado.screens.payload_fragment.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_payload.*
import org.greenrobot.eventbus.EventBus

class PayloadsAdapter(var data: MutableList<Payload>) : RecyclerView.Adapter<PayloadsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    fun updateList() {
        this.data = PayloadHelper.getAll()

        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_payload, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(payload: Payload) {
            itemPayloadName.text = payload.name

            itemPayloadRemove.visibility = if (payload.name == PayloadHelper.BUNDLED_PAYLOAD_SX || payload.name == PayloadHelper.BUNDLED_PAYLOAD_REINX) View.GONE else View.VISIBLE

            itemPayloadRemove.setOnClickListener {
                MemoryUtils.removeFile(payload.path)
                EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
                LogHelper.log(LogHelper.INFO, "Payload ${payload.name} deleted!")
            }
        }

    }
}
