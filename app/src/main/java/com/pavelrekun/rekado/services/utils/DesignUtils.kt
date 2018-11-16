package com.pavelrekun.rekado.services.utils

import android.app.ActivityManager
import android.graphics.BitmapFactory
import android.os.Build
import android.util.TypedValue
import android.view.View
import androidx.core.content.ContextCompat
import androidx.preference.PreferenceManager
import com.pavelrekun.rang.data.NightMode
import com.pavelrekun.rang.data.PrimaryColor
import com.pavelrekun.rang.services.Rang
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.RekadoApplication
import com.pavelrekun.rekado.base.BaseActivity


object DesignUtils {

    fun setNightTheme() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(RekadoApplication.instance.applicationContext)
        val nightMode = preferences.getString("appearance_night_mode", "enabled")

        when (nightMode) {
            "disabled" -> Rang.config(RekadoApplication.instance.applicationContext).nightMode(NightMode.DAY).oledMode(false).apply()
            "enabled" -> Rang.config(RekadoApplication.instance.applicationContext).nightMode(NightMode.NIGHT).oledMode(false).apply()
            "amoled" -> Rang.config(RekadoApplication.instance.applicationContext).primaryColor(PrimaryColor.CASTRO_OLED).nightMode(NightMode.NIGHT).oledMode(true).apply()
        }
    }

    private fun changeStatusBarMode(nightMode: NightMode, view: View) {
        when (nightMode) {
            NightMode.DAY -> setLightStatusBar(view)
            NightMode.NIGHT -> clearLightStatusBar(view)
        }
    }

    private fun setLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            view.systemUiVisibility = flags
        }
    }

    private fun clearLightStatusBar(view: View) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            var flags = view.systemUiVisibility
            flags = flags and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
            view.systemUiVisibility = flags
        }
    }

    fun applyColorToTaskDescription(activity: BaseActivity) {
        val bitmap = BitmapFactory.decodeResource(activity.resources, R.mipmap.ic_launcher)
        val taskDescription = ActivityManager.TaskDescription(activity.getString(R.string.app_name), bitmap, ContextCompat.getColor(activity, R.color.colorBackgroundPrimary))
        activity.setTaskDescription(taskDescription)
    }

    fun convertDPtoPX(dp: Int): Int {
        val resources = RekadoApplication.instance.applicationContext.resources
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp.toFloat(), resources.displayMetrics).toInt()
    }

}