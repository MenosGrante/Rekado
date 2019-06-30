package com.pavelrekun.rekado.screens.payload_fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.Logger
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class PayloadsFragment : Fragment() {

    private lateinit var mvpView: PayloadsContract.View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_payloads, container, false)
        val activity = activity as BaseActivity

        mvpView = PayloadsView(activity, this)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mvpView.initViews()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        mvpView.onActivityResult(requestCode, resultCode, data)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Events.UpdatePayloadsListEvent) {
        mvpView.updateList()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: Events.PayloadDownloadedSuccessfully) {
        mvpView.updateList()
        Logger.info("Payload ${event.payloadName} downloaded successfully.")
    }

    override fun onResume() {
        super.onResume()
        mvpView.onResume()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
}