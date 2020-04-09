package com.pavelrekun.rekado.screens.translators_fragment

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.rekado.data.Translator
import com.pavelrekun.rekado.databinding.ItemTranslatorBinding

class TranslatorsAdapter(val data: MutableList<Translator>) : RecyclerView.Adapter<TranslatorsAdapter.ViewHolder>() {

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTranslatorBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    class ViewHolder(private val binding: ItemTranslatorBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(translator: Translator) {
            binding.translatorLanguage.text = translator.language
            binding.translatorPeople.text = translator.people
        }

    }
}