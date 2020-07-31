package com.pavelrekun.rekado.screens.translators_fragment.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.magta.translators.Language
import com.pavelrekun.rekado.databinding.ItemTranslatorBinding

class TranslatorsAdapter(val data: List<Language>) : RecyclerView.Adapter<TranslatorsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(data[position])

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = ItemTranslatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemView)
    }

    class ViewHolder(private val binding: ItemTranslatorBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(language: Language) {
            binding.itemTranslatorFlag.setImageResource(language.flag)
            binding.itemTranslatorLanguage.setText(language.language)
            binding.itemTranslatorList.setText(language.translators)
        }

    }
}