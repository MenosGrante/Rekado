package com.pavelrekun.rekado.services.utils

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog

import com.pavelrekun.rekado.R

object PermissionsUtils {

    const val PERMISSIONS_READ_REQUEST_CODE = 125
    const val PERMISSIONS_WRITE_REQUEST_CODE = 126

    private fun requestPermissions(fragment: Fragment, permissions: Array<String>, code: Int) {
        fragment.requestPermissions(permissions, code)
    }

    fun checkPermissionGranted(activity: Activity, permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(activity, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    fun showPermissionDialog(activity: Activity, fragment: Fragment, code: Int) {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(R.string.permission_storage_dialog_title)
        builder.setMessage(R.string.permission_storage_dialog_description)

        val storagePermissionDialog = builder.create()
        storagePermissionDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.permission_storage_button)) { _, _ ->
            storagePermissionDialog.dismiss()
            requestPermissions(fragment, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), code)
        }

        storagePermissionDialog.show()
    }
}