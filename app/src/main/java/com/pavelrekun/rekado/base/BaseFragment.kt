package com.pavelrekun.rekado.base

import android.os.Bundle
import android.view.Menu
import androidx.fragment.app.Fragment
import com.pavelrekun.rekado.screens.instructions_fragment.InstructionsFragment
import com.pavelrekun.rekado.screens.logs_fragment.LogsFragment
import com.pavelrekun.rekado.screens.navigation_activity.NavigationActivity
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsFragment
import com.pavelrekun.rekado.screens.tools_fragment.ToolsFragment

open class BaseFragment(layoutRes: Int) : Fragment(layoutRes) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        val shouldDisplayOptionsMenu =
            this is PayloadsFragment || this is ToolsFragment || this is InstructionsFragment || this is LogsFragment
        if (!shouldDisplayOptionsMenu) {
            menu.clear()
        }
    }

    fun requireBaseActivity() = activity as NavigationActivity

    fun navigate(id: Int) {
        (requireActivity() as NavigationActivity).navigate(id)
    }

    fun showProgress() {
        (requireActivity() as NavigationActivity).showProgress()
    }

    fun hideProgress() {
        (requireActivity() as NavigationActivity).hideProgress()
    }


}