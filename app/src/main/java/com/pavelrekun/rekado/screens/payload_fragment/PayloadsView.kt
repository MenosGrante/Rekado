package com.pavelrekun.rekado.screens.payload_fragment

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.pavelrekun.penza.services.extensions.tintContrast
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.payload_fragment.adapters.PayloadsAdapter
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.Constants.KEY_OPEN_PAYLOAD
import com.pavelrekun.rekado.services.Events
import com.pavelrekun.rekado.services.Logger
import com.pavelrekun.rekado.services.dialogs.DialogsShower
import com.pavelrekun.rekado.services.extensions.extractFileName
import com.pavelrekun.rekado.services.extensions.extractName
import com.pavelrekun.rekado.services.extensions.toFile
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.SettingsUtils
import kotlinx.android.synthetic.main.fragment_payloads.*
import org.greenrobot.eventbus.EventBus


class PayloadsView(private val activity: BaseActivity, private val fragment: Fragment) : PayloadsContract.View {

    private lateinit var adapter: PayloadsAdapter

    override fun initViews() {
        activity.setTitle(R.string.navigation_payloads)

        initList()
        initClickListeners()
        initDesign()
    }

    override fun initList() {
        if (!SettingsUtils.checkHideBundledEnabled()) {
            MemoryUtils.copyBundledPayloads()
        }

        adapter = PayloadsAdapter(PayloadHelper.getAllPayloads())

        activity.payloadsList.setHasFixedSize(true)
        activity.payloadsList.layoutManager = LinearLayoutManager(activity)
        activity.payloadsList.adapter = adapter
    }

    override fun initDesign() {
        activity.payloadsAddUrl.tintContrast()
        activity.payloadsAdd.tintContrast()
    }

    override fun updateList() {
        if (this::adapter.isInitialized) {
            adapter.updateList()
        }
    }

    override fun initClickListeners() {
        activity.payloadsAddUrl.setOnClickListener { DialogsShower.showPayloadsDownloadDialog(activity) }
        activity.payloadsAdd.setOnClickListener { addPayload() }
    }

    override fun addPayload() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = Constants.MIME_BINARY
        intent.putExtra(Intent.EXTRA_TITLE, R.string.dialog_file_chooser_payload_title)
        fragment.startActivityForResult(intent, KEY_OPEN_PAYLOAD)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == KEY_OPEN_PAYLOAD) {
            when (resultCode) {
                Activity.RESULT_OK -> data?.data?.let {
                    val name = it.extractFileName()
                    if (name != null) {
                        val inputStream = activity.contentResolver.openInputStream(it)

                        if (inputStream != null) {
                            inputStream.toFile("${PayloadHelper.getLocation().absolutePath}/$name")
                            EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
                            Logger.info("Added new payload: $name")
                        } else {
                            Toast.makeText(activity, activity.getString(R.string.helper_error_adding_payload), Toast.LENGTH_SHORT).show()
                            Logger.error("Failed to add payload: $name")
                        }
                        activity.contentResolver.openInputStream(it)?.toFile(PayloadHelper.getLocation().absolutePath + "/" + it.path?.extractName())
                    } else {
                        Toast.makeText(activity, activity.getString(R.string.helper_error_adding_payload), Toast.LENGTH_SHORT).show()
                        Logger.error("Failed to add selected payload!")
                    }
                }
            }
        }
    }

    override fun onResume() {
        if (this::adapter.isInitialized) {
            initList()
        }
    }
}