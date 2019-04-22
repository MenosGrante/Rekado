package com.pavelrekun.rekado.screens.payload_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.widget.Toast
import com.pavelrekun.konae.Konae
import com.pavelrekun.konae.filters.ExtensionFileFilter
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.data.Payload
import com.pavelrekun.rekado.screens.payload_fragment.adapters.PayloadsAdapter
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.payloads.PayloadHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import com.pavelrekun.rekado.services.utils.SettingsUtils
import com.pavelrekun.siga.services.extensions.tintIconReverse
import kotlinx.android.synthetic.main.fragment_payloads.*
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import java.util.concurrent.TimeUnit
import org.greenrobot.eventbus.EventBus
import java.nio.charset.Charset
import android.content.DialogInterface
import android.text.Editable
import android.widget.EditText
import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import kotlinx.coroutines.*
import java.io.*
import okio.Okio

class PayloadsView(private val activity: BaseActivity, private val fragment: androidx.fragment.app.Fragment) : PayloadsContract.View {

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
        if (!SettingsUtils.checkHideBundledEnabled()) {
            MemoryUtils.copyBundledPayloads()
        }

        adapter = PayloadsAdapter(PayloadHelper.getAll())

        activity.payloadsList.setHasFixedSize(true)
        activity.payloadsList.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(activity)
        activity.payloadsList.adapter = adapter
    }

    override fun initDesign() {
        activity.payloadsAddUrl.tintIconReverse()
        activity.payloadsAdd.tintIconReverse()
    }

    override fun updateList() {
        if (this::adapter.isInitialized) {
            adapter.updateList()
        }
    }

    override fun initClickListeners() {
        activity.payloadsAddUrl.setOnClickListener { addPayloadUrl() }
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
        Konae().with(activity).withChosenListener(object : Konae.Result {
            override fun onChoosePath(dirFile: File) {
                onChosenFileListener(dirFile)
            }
        }).withFileFilter(ExtensionFileFilter("bin")).withTitle(activity.getString(R.string.dialog_file_chooser_payload_title)).build().show()
    }

    private fun onChosenFileListener(pathFile: File) {
        val payload = Payload(PayloadHelper.getName(pathFile.absolutePath), PayloadHelper.getPath(PayloadHelper.getName(pathFile.absolutePath)))

        if (!payload.name.contains("bin")) {
            Toast.makeText(activity, activity.getString(R.string.helper_error_file_payload_wrong), Toast.LENGTH_SHORT).show()
        }

        try {
            MemoryUtils.toFile(pathFile, "${PayloadHelper.FOLDER_PATH}/${payload.name}")

            EventBus.getDefault().post(Events.UpdatePayloadsListEvent())
            LogHelper.log(LogHelper.INFO, "Added new payload: ${payload.name}")
        } catch (e: IOException) {
            e.printStackTrace()
            LogHelper.log(LogHelper.ERROR, "Failed to add payload: ${payload.name}")
        }
    }


    private val uiDispatcher: CoroutineDispatcher = Dispatchers.Main

    fun addPayloadFromUrl(filename: String, url: String) = GlobalScope.launch(uiDispatcher) {

        val path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Rekado/"
        val client = OkHttpClient()

        LogHelper.log(LogHelper.INFO, "rekado DOWNLOADING!!!!!!!! " + filename + " from "+url)

        withContext(Dispatchers.IO) {
            LogHelper.log(LogHelper.INFO, "rekado DOWNLOADING2222 " + filename + " from "+url)

            try {
                val request = Request.Builder()
                        .url(url)
                        .build()

                val response = client.newCall(request).execute()


                val Directory = File(path)
                if (!Directory.exists()) {
                    Directory.mkdirs()
                }

                val sink = Okio.buffer(Okio.sink(File(Directory, filename)))
                sink.writeAll(response.body()?.source())
                sink.close()
                response.body()?.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    }



    fun addPayloadUrl() {
        val builder = AlertDialog.Builder(activity)
        val dialogLayout = LayoutInflater.from(activity).inflate(R.layout.dialog_payload_download, null)
        builder.setTitle(R.string.dialog_payload_download_title)
        val payloadname  = dialogLayout.findViewById<EditText>(R.id.payload_name)
        val payloadurl  = dialogLayout.findViewById<EditText>(R.id.payload_url)
        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.dialog_payload_download) {
            dialogInterface, i ->
            addPayloadFromUrl(payloadname.text.toString(), payloadurl.text.toString())
            Toast.makeText(activity, "Downloading " + payloadname.text.toString() + " from " + payloadurl.text.toString(), Toast.LENGTH_LONG).show()

        }
        builder.show()
    }


/*

    fun addPayloadUrlOld() {
        val alert = AlertDialog.Builder(activity)
        val editurl = EditText(activity)
        alert.setMessage("Enter Your Payload's url")
        alert.setTitle("get your payload from a url")

        alert.setView(editurl)

        alert.setPositiveButton("Download", DialogInterface.OnClickListener { dialog, whichButton ->
            payloadurl = editurl.text.toString()
            addPayloadFromUrl(payloadurl, "ownpayload.bin")
        })

        alert.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
            // what ever you want to do with No option.
        })

        alert.show()

    }
    */

    override fun onResume() {
        if (this::adapter.isInitialized) {
            prepareList()
        }
    }
}