package com.pavelrekun.rekado.screens.translators_fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.data.Translator
import com.pavelrekun.rekado.databinding.FragmentTranslatorsBinding
import com.pavelrekun.rekado.services.extensions.viewBinding

class TranslatorsFragment : BaseFragment(R.layout.fragment_translators) {

    private val binding by viewBinding(FragmentTranslatorsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScrollingBehaviour(binding.translatorsList)
        initList()
    }

    private fun initList() {
        val translatorsLanguages = resources.getStringArray(R.array.translators_languages)
        val translatorsPeople = resources.getStringArray(R.array.translators_people)

        val translatorsList = arrayListOf<Translator>()

        for (i in translatorsLanguages.indices) {
            translatorsList.add(Translator(translatorsPeople[i], translatorsLanguages[i]))
        }

        binding.translatorsList.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(getBaseActivity())
            adapter = TranslatorsAdapter(translatorsList)
        }
    }

}