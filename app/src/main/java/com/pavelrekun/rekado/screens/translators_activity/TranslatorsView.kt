package com.pavelrekun.rekado.screens.translators_activity

import androidx.recyclerview.widget.LinearLayoutManager
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.translators_activity.adapters.TranslatorsAdapter
import com.pavelrekun.rekado.screens.translators_activity.data.TranslatorData
import kotlinx.android.synthetic.main.activity_translators.*
import java.util.*

class TranslatorsView(private val activity: BaseActivity) : TranslatorsContract.View {

    init {
        onViewCreated()
    }

    override fun onViewCreated() {
        initToolbar()
        initList()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.translatorsToolbar)
        activity.setTitle(R.string.navigation_translators)

        activity.translatorsToolbar.setNavigationIcon(R.drawable.ic_navigation_back)
        activity.translatorsToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    override fun initList() {
        val translatorsLanguages = activity.resources.getStringArray(R.array.translators_languages)
        val translatorsPeople = activity.resources.getStringArray(R.array.translators_people)

        val translatorModelsList = ArrayList<TranslatorData>()

        for (i in translatorsLanguages.indices) {
            translatorModelsList.add(TranslatorData(translatorsPeople[i], translatorsLanguages[i]))
        }

        activity.translatorsList.setHasFixedSize(true)
        activity.translatorsList.layoutManager = LinearLayoutManager(activity)
        activity.translatorsList.adapter = TranslatorsAdapter(translatorModelsList)
    }
}