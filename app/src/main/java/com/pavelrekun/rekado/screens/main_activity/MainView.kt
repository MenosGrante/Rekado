package com.pavelrekun.rekado.screens.main_activity

import android.app.Activity
import android.content.Intent
import android.support.v4.app.Fragment
import android.widget.Toast
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.screens.instructions_fragment.InstructionsFragment
import com.pavelrekun.rekado.screens.logs_fragment.LogsFragment
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsFragment
import com.pavelrekun.rekado.screens.payload_fragment.PayloadsView
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.logs.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.FilesHelper
import kotlinx.android.synthetic.main.activity_main.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException


class MainView(private val activity: BaseActivity) : MainContract.View {

    init {
        initViews()
    }

    override fun initViews() {
        initNavigationClickListener()
        initToolbar()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.mainToolbar)
    }

    override fun initNavigationClickListener() {
        chooseNavigationItem(R.id.navigation_instructions)
        activity.mainNavigationBar.selectedItemId = R.id.navigation_instructions

        activity.mainNavigationBar.setOnNavigationItemSelectedListener {
            chooseNavigationItem(it.itemId)
            true
        }
    }

    private fun chooseNavigationItem(id: Int) {
        var fragment: Fragment? = null

        when (id) {
            R.id.navigation_payloads -> fragment = PayloadsFragment()
            R.id.navigation_instructions -> fragment = InstructionsFragment()
            R.id.navigation_logs -> fragment = LogsFragment()
        }

        if (fragment != null) {
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.main_content_frame, fragment)
            transaction.commitNow()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        if (requestCode == PayloadsView.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK && resultData != null) {

            val payload = Payload(PayloadHelper.getName(resultData.data.path), PayloadHelper.getPath(PayloadHelper.getName(resultData.data.path)))

            if (File(payload.path).extension != "bin") {
                Toast.makeText(activity, activity.getString(R.string.helper_error_file_wrong), Toast.LENGTH_SHORT).show()
                return
            }

            try {
                val inputStream = activity.contentResolver.openInputStream(resultData.data)
                FilesHelper.toFile(inputStream, PayloadHelper.FOLDER_PATH + "/" + payload.name)

                EventBus.getDefault().postSticky(Events.UpdateListEvent())
                Logger.log(1, "Added new payload: ${payload.name}")
            } catch (e: IOException) {
                e.printStackTrace()
                Logger.log(0, "Failed to add payload: ${payload.name}")
            }


        }
    }
}