package com.pavelrekun.rekado.base

import androidx.fragment.app.Fragment
import com.pavelrekun.penza.widgets.ElevationRecyclerView
import com.pavelrekun.penza.widgets.ElevationScrollView

open class BaseFragment(layoutRes: Int = 0) : Fragment(layoutRes) {

    fun initWithTitle(resId: Int) = getBaseActivity().setTitle(resId)

    fun getBaseActivity() = activity as BaseActivity

    fun initScrollingBehaviour(scrollView: ElevationScrollView) = scrollView.setInstance(getBaseActivity())

    fun initScrollingBehaviour(recyclerView: ElevationRecyclerView) = recyclerView.setInstance(getBaseActivity())

}