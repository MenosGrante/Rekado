package com.pavelrekun.rekado.screens.payload_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.obsez.android.lib.filechooser.ChooserDialog
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.screens.payload_fragment.adapters.PayloadsAdapter
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.logs.Logger
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.FilesHelper
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import com.pavelrekun.rekado.services.utils.toFile
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
    }

    override fun prepareList() {
        if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity, fragment, PermissionsUtils.PERMISSIONS_READ_REQUEST_CODE)
        } else {
            initList()
        }
    }

    override fun initList() {
        FilesHelper.copyAsset()

        adapter = PayloadsAdapter(PayloadHelper.getPayloads())

        activity.payloadsList.setHasFixedSize(true)
        activity.payloadsList.layoutManager = LinearLayoutManager(activity)
        activity.payloadsList.adapter = adapter
    }

    override fun updateList() {
        if (this::adapter.isInitialized) {
            adapter.updateList(PayloadHelper.getPayloads())
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
        ChooserDialog().with(activity)
                .withFilter(false, false, "bin")
                .withStartFile(Environment.getExternalStorageDirectory().path)
                .withResources(R.string.dialog_loader_title, R.string.dialog_positive, R.string.dialog_negative)
                .withRowLayoutView(R.layout.item_dialog_chooser)
                .withChosenListener { path, pathFile -> onChosenFileListener(path, pathFile) }
                .build()
                .show()
    }

    private fun onChosenFileListener(path: String, pathFile: File) {
        val payload = Payload(PayloadHelper.getName(path), PayloadHelper.getPath(PayloadHelper.getName(path)))

        if (!payload.name.contains("bin")) {
            Toast.makeText(activity, activity.getString(R.string.helper_error_file_wrong), Toast.LENGTH_SHORT).show()
        }

        try {
            pathFile.toFile(PayloadHelper.FOLDER_PATH + "/" + payload.name)

            EventBus.getDefault().postSticky(Events.UpdateListEvent())
            Logger.log(1, "Added new payload: ${payload.name}")
        } catch (e: IOException) {
            e.printStackTrace()
            Logger.log(0, "Failed to add payload: ${payload.name}")
        }
    }
}