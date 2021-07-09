package com.pavelrekun.rekado.base

import androidx.fragment.app.Fragment
import com.pavelrekun.magta.views.ElevationRecyclerView
import com.pavelrekun.magta.views.ElevationScrollView

open class BaseFragment(layoutRes: Int = 0) : Fragment(layoutRes) {

    fun initWithTitle(resId: Int) = requireBaseActivity().setTitle(resId)

    fun requireBaseActivity() = activity as BaseActivity

    fun initScrollingBehaviour(scrollView: ElevationScrollView) = scrollView.setInstance(requireBaseActivity())

    fun initScrollingBehaviour(recyclerView: ElevationRecyclerView) = recyclerView.setInstance(requireBaseActivity())

}