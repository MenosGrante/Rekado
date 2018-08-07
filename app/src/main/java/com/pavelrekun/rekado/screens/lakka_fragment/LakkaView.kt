package com.pavelrekun.rekado.screens.lakka_fragment

import android.Manifest
import android.content.pm.PackageManager
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.widget.Toast
import com.pavelrekun.konae.Konae
import com.pavelrekun.konae.filters.ExtensionFileFilter
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.dialogs.Dialogs
import com.pavelrekun.rekado.services.eventbus.Events
import com.pavelrekun.rekado.services.lakka.LakkaHelper
import com.pavelrekun.rekado.services.logs.LogHelper
import com.pavelrekun.rekado.services.utils.MemoryUtils
import com.pavelrekun.rekado.services.utils.PermissionsUtils
import kotlinx.android.synthetic.main.fragment_lakka.*
import org.greenrobot.eventbus.EventBus
import java.io.File
import java.io.IOException

class LakkaView(private val activity: BaseActivity, private val fragment: Fragment) : LakkaContract.View {

    override fun initViews() {
        activity.setTitle(R.string.navigation_lakka)

        initCBFSCategory()
        initClickListeners()

        if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            PermissionsUtils.showPermissionDialog(activity, fragment, PermissionsUtils.PERMISSIONS_READ_REQUEST_CODE)
        } else {
            initCorebootCategory()
        }
    }

    override fun initCBFSCategory() {
        if (LakkaHelper.checkCBFSPresent()) {
            activity.lakkaCBFSStatus.text = activity.getString(R.string.lakka_tools_ready)
            activity.lakkaCBFSStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lakka_ready, 0, 0, 0)
            activity.lakkaCBFSStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen))
        } else {
            activity.lakkaCBFSStatus.text = activity.getString(R.string.lakka_tools_not_ready)
            activity.lakkaCBFSStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lakka_not_ready, 0, 0, 0)
            activity.lakkaCBFSStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed))
        }

        activity.lakkaCBFSTitle.text = activity.getString(R.string.lakka_category_cbfs) + " | " + LakkaHelper.PAYLOAD_UPDATE_DATE
    }

    override fun initCorebootCategory() {
        if (LakkaHelper.checkCorebootPresent()) {
            activity.lakkaCorebootStatus.text = activity.getString(R.string.lakka_tools_ready)
            activity.lakkaCorebootStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lakka_ready, 0, 0, 0)
            activity.lakkaCorebootStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorGreen))
        } else {
            activity.lakkaCorebootStatus.text = activity.getString(R.string.lakka_tools_not_ready)
            activity.lakkaCorebootStatus.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_lakka_not_ready, 0, 0, 0)
            activity.lakkaCorebootStatus.setTextColor(ContextCompat.getColor(activity, R.color.colorRed))
        }

        if (LakkaHelper.checkCorebootPresent()) {
            activity.lakkaCorebootTitle.text = activity.getString(R.string.lakka_category_coreboot) + " | " + LakkaHelper.getCorebootUpdateDate()
            activity.lakkaCorebootButton.text = activity.getString(R.string.lakka_button_update)
        } else {
            activity.lakkaCorebootTitle.text = activity.getString(R.string.lakka_category_coreboot)
            activity.lakkaCorebootButton.text = activity.getString(R.string.lakka_button_add)
        }

        activity.lakkaCorebootHelp.setOnClickListener { Dialogs.showDialog(activity, R.string.lakka_coreboot_dialog_title, R.string.lakka_coreboot_dialog_description) }
    }

    override fun initClickListeners() {
        activity.lakkaCorebootButton.setOnClickListener {
            if (!PermissionsUtils.checkPermissionGranted(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                PermissionsUtils.showPermissionDialog(activity, fragment, PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE)
            } else {
                getCorebootFromStorage()
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PermissionsUtils.PERMISSIONS_READ_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                initCorebootCategory()
            }

            PermissionsUtils.PERMISSIONS_WRITE_REQUEST_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCorebootFromStorage()
            } else {
                Toast.makeText(activity, R.string.permission_storage_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getCorebootFromStorage() {
        Konae().with(activity)
                .withChosenListener(object : Konae.Result {
                    override fun onChoosePath(dirFile: File) {
                        onChosenFileListener(dirFile)
                    }
                })
                .withFileFilter(ExtensionFileFilter("rom"))
                .withTitle(activity.getString(R.string.dialog_file_chooser_coreboot_title))
                .build()
                .show()
    }

    private fun onChosenFileListener(file: File) {
        if (!file.name.contains("rom")) {
            Toast.makeText(activity, activity.getString(R.string.helper_error_file_coreboot_wrong), Toast.LENGTH_SHORT).show()
        }

        try {
            MemoryUtils.toFile(file, LakkaHelper.FOLDER_PATH + "/coreboot.rom")
            EventBus.getDefault().post(Events.UpdateCorebootEvent())
            LogHelper.log(LogHelper.INFO, "Added coreboot file!")
        } catch (e: IOException) {
            e.printStackTrace()
            LogHelper.log(LogHelper.ERROR, "Failed to add coreboot file!")
        }
    }
}