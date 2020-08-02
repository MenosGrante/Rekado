package com.pavelrekun.rekado.screens.translators_fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavelrekun.magta.translators.Language
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentTranslatorsBinding
import com.pavelrekun.rekado.screens.translators_fragment.adapters.TranslatorsAdapter
import com.pavelrekun.rekado.services.extensions.viewBinding
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge

class TranslatorsFragment : BaseFragment(R.layout.fragment_translators) {

    private val binding by viewBinding(FragmentTranslatorsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScrollingBehaviour(binding.translatorsData)
        initList()
        initEdgeToEdge()
    }

    private fun initList() {
        val languagesList = arrayListOf<Language>()

        languagesList.add(Language.english(R.string.translators_english))
        languagesList.add(Language.russian(R.string.translators_russian))
        languagesList.add(Language.ukrainian(R.string.translators_ukrainian))
        languagesList.add(Language.spanish(R.string.translators_spanish))

        binding.translatorsData.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireBaseActivity())
            adapter = TranslatorsAdapter(languagesList)
        }
    }

    private fun initEdgeToEdge() {
        edgeToEdge {
            binding.translatorsData.fit { Edge.Bottom }
        }
    }

}