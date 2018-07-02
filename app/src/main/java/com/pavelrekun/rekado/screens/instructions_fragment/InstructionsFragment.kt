package com.pavelrekun.rekado.screens.instructions_fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity

class InstructionsFragment : Fragment() {

    private lateinit var mvpView: InstructionsContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_instrutions, container, false)
        val activity = activity as BaseActivity

        mvpView = InstructionsView(activity)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpView.initViews()
    }
}