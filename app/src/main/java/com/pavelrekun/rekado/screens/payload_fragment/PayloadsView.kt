package com.pavelrekun.rekado.screens.payload_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.pavelrekun.konae.Konae
import com.pavelrekun.konae.filters.ExtensionFileFilter
import com.pavelrekun.rang.utils.ColorsHelper
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.screens.payload_fragment.adapters.PayloadsAdapter
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import kotlinx.android.synthetic.main.fragment_payloads.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException


class PayloadsView(private val activity: BaseActivity, private val fragment: Fragment) : PayloadsContract.View {

    private lateinit var adapter: PayloadsAdapter

    override fun initViews() {
        activity.setTitle(R.string.navigation_payloads)

        prepareList()
        initClickListeners()
        initDesign()
    }

    override fun prepareList() {
        if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity, fragment, PermissionsUtils.PERMISSIONS_READ_REQUEST_CODE)
        } else {
            initList()
        }
    }

    override fun initList() {
        MemoryUtils.copyBundledPayloads()

        adapter = PayloadsAdapter(PayloadHelper.getAll())

        activity.payloadsList.setHasFixedSize(true)
        activity.payloadsList.layoutManager = LinearLayoutManager(activity)
        activity.payloadsList.adapter = adapter
    }

    override fun initDesign() {
        activity.payloadsAdd.setColorFilter(ColorsHelper.getContrastColor(activity, ColorsHelper.resolveAccentColor(activity)))
    }

    override fun updateList() {
        if (this::adapter.isInitialized) {
            adapter.updateList()
        }
    }

    override fun initClickListeners() {
        activity.payloadsAdd.setOnClickListener { addPayload() }
    }

    override fun addPayload() {
        if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity, fragment, PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE)
        } else {
            getPayloadFromStorage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionsUtils.PERMISSIONS_READ_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initList()
            }
            PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPayloadFromStorage()
            } else {
                Toast.makeText(activity, R.string.permission_storage_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPayloadFromStorage() {
        Konae().with(activity)
                .withChosenListener(object : Konae.Result {
                    override fun onChoosePath(dirFile: File) {
                        onChosenFileListener(dirFile)
                    }
                })
                .withFileFilter(ExtensionFileFilter("bin"))
                .withTitle(activity.getString(R.string.dialog_file_chooser_payload_title))
                .build()
                .show()
    }

    private fun onChosenFileListener(pathFile: File) {
        val payload = Payload(PayloadHelper.getName(pathFile.absolutePath), PayloadHelper.getPath(PayloadHelper.getName(pathFile.absolutePath)))

        if (!payload.name.contains("bin")) {
            Toast.makeText(activity, activity.getString(R.string.helper_error_file_payload_wrong), Toast.LENGTH_SHORT).show()
        }

        try {
            MemoryUtils.toFile(pathFile, (PayloadHelper.FOLDER_PATH + "/" + payload.name))

            EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
            LogHelper.log(LogHelper.INFO, "Added new payload: ${payload.name}")
        } catch (e: IOException) {
            e.printStackTrace()
            LogHelper.log(LogHelper.ERROR, "Failed to add payload: ${payload.name}")
        }
    }
}