package com.pavelrekun.rekado.screens.translators_activity.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.screens.translators_activity.data.TranslatorData
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_list_translator.*

class TranslatorsAdapter(val data: MutableList<TranslatorData>) : RecyclerView.Adapter<TranslatorsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_list_translator, parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(override val containerView: View) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(translator: TranslatorData) {
            translatorLanguage.text = translator.language
            translatorPeople.text = translator.people
        }

    }
}