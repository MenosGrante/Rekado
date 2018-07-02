package com.pavelrekun.rekado.services.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;

import com.pavelrekun.rekado.R;

public class PermissionsUtils {

    public static final int PERMISSIONS_REQUEST_CODE = 125;

    private static void requestPermissions(Fragment fragment, String[] permissions) {
        fragment.requestPermissions(permissions, PERMISSIONS_REQUEST_CODE);
    }

    public static boolean checkPermissionGranted(Activity activity, String permission) {
        int result = ContextCompat.checkSelfPermission(activity, permission);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    public static void showPermissionDialog(Activity activity, Fragment fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(R.string.permission_storage_dialog_title);
        builder.setMessage(R.string.permission_storage_dialog_description);

        AlertDialog storagePermissionDialog = builder.create();
        storagePermissionDialog.setButton(AlertDialog.BUTTON_POSITIVE, activity.getString(R.string.permission_storage_button), (dialogInterface, i) -> {
            storagePermissionDialog.dismiss();
            requestPermissions(fragment, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE});
        });

        storagePermissionDialog.show();
    }
}