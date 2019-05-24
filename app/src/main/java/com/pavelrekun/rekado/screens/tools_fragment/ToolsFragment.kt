package com.pavelrekun.rekado.screens.tools_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class ToolsFragment : Fragment() {

    private lateinit var mvpView: ToolsContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_tools, container, false)
        val activity = activity as BaseActivity

        mvpView = ToolsView(activity, view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpView.onViewCreated()
    }

}