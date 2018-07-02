package com.pavelrekun.rekado.screens.payload_fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.screens.payload_fragment.adapters.PayloadsAdapter
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.FilesHelper
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import kotlinx.android.synthetic.main.fragment_payloads.*


class PayloadsView(private val activity: BaseActivity, private val fragment: Fragment) : PayloadsContract.View {

    companion object {
        const val READ_REQUEST_CODE = 42
    }

    private lateinit var adapter: PayloadsAdapter

    override fun initViews() {
        activity.setTitle(R.string.navigation_payloads)

        initList()
        initClickListeners()
    }

    override fun initList() {
        adapter = PayloadsAdapter(PayloadHelper.getPayloads())

        activity.payloadsList.setHasFixedSize(true)
        activity.payloadsList.layoutManager = LinearLayoutManager(activity)
        activity.payloadsList.adapter = adapter
    }

    override fun updateList() {
        adapter.notifyDataSetChanged()
    }

    override fun initClickListeners() {
        activity.payloadsAdd.setOnClickListener { addPayload() }
    }

    override fun addPayload() {
        if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity, fragment)
        } else {
            getPayloadFromStorage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionsUtils.PERMISSIONS_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPayloadFromStorage()
                FilesHelper.copyAsset()
            } else {
                Toast.makeText(activity, R.string.permission_storage_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getPayloadFromStorage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "*/.bin*"
        activity.startActivityForResult(intent, READ_REQUEST_CODE)
    }
}