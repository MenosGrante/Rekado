package com.pavelrekun.rekado.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.recyclerview.widget.RecyclerView
import com.pavelrekun.penza.services.extensions.convertPXToDP

open class BasePreferencesFragment(private val preferencesLayoutId: Int, private val titleResId: Int) : PreferenceFragmentCompat() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireBaseActivity().supportActionBar?.setTitle(titleResId)
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(preferencesLayoutId)
    }

    override fun <T : Preference> findPreference(key: CharSequence): T {
        return super.findPreference(key)!!
    }

    override fun onCreateRecyclerView(inflater: LayoutInflater?, parent: ViewGroup?, savedInstanceState: Bundle?): RecyclerView {
        val view = super.onCreateRecyclerView(inflater, parent, savedInstanceState)

        view.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                if (view.canScrollVertically(SCROLL_DIRECTION_UP)) {
                    requireBaseActivity().supportActionBar?.elevation = 3.convertPXToDP(requireBaseActivity()).toFloat()
                } else {
                    requireBaseActivity().supportActionBar?.elevation = 0F
                }
            }

        })

        return view
    }

    fun requireBaseActivity() = activity as BaseActivity

    companion object {
        private const val SCROLL_DIRECTION_UP = -1
    }
}