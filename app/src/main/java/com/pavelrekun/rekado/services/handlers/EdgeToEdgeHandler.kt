package com.pavelrekun.rekado.services.handlers

import android.content.Context
import android.graphics.Color
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.view.View
import android.view.Window
import androidx.core.graphics.ColorUtils
import com.google.android.material.color.MaterialColors
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@Suppress("DEPRECATION")
@ActivityScoped
class EdgeToEdgeHandler @Inject constructor(@ActivityContext private val context: Context) {

    private val isEdgeToEdgeEnabled = VERSION.SDK_INT >= VERSION_CODES.Q

    fun applyEdgeToEdgePreference(window: Window) {
        val edgeToEdgeEnabled = isEdgeToEdgeEnabled
        val statusBarColor = getStatusBarColor(isEdgeToEdgeEnabled)
        val navbarColor = getNavBarColor(isEdgeToEdgeEnabled)
        val lightBackground = MaterialColors.isColorLight(MaterialColors.getColor(context, android.R.attr.colorBackground, Color.BLACK))
        val lightStatusBar = MaterialColors.isColorLight(statusBarColor)
        val showDarkStatusBarIcons = lightStatusBar || statusBarColor == Color.TRANSPARENT && lightBackground
        val lightNavbar = MaterialColors.isColorLight(navbarColor)
        val showDarkNavbarIcons = lightNavbar || navbarColor == Color.TRANSPARENT && lightBackground
        val decorView = window.decorView
        val currentStatusBar = if (showDarkStatusBarIcons && VERSION.SDK_INT >= VERSION_CODES.M) View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR else 0
        val currentNavBar = if (showDarkNavbarIcons && VERSION.SDK_INT >= VERSION_CODES.O) View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR else 0
        window.navigationBarColor = navbarColor
        window.statusBarColor = statusBarColor
        val systemUiVisibility = ((if (edgeToEdgeEnabled) EDGE_TO_EDGE_FLAGS else View.SYSTEM_UI_FLAG_VISIBLE)
                or currentStatusBar
                or currentNavBar)
        decorView.systemUiVisibility = systemUiVisibility
    }

    private fun getStatusBarColor(isEdgeToEdgeEnabled: Boolean): Int {
        if (isEdgeToEdgeEnabled && VERSION.SDK_INT < VERSION_CODES.M) {
            val opaqueStatusBarColor = MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK)
            return ColorUtils.setAlphaComponent(opaqueStatusBarColor, EDGE_TO_EDGE_BAR_ALPHA)
        }
        return if (isEdgeToEdgeEnabled) {
            Color.TRANSPARENT
        } else MaterialColors.getColor(context, android.R.attr.statusBarColor, Color.BLACK)
    }

    private fun getNavBarColor(isEdgeToEdgeEnabled: Boolean): Int {
        if (isEdgeToEdgeEnabled && VERSION.SDK_INT < VERSION_CODES.O_MR1) {
            val opaqueNavBarColor = MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK)
            return ColorUtils.setAlphaComponent(opaqueNavBarColor, EDGE_TO_EDGE_BAR_ALPHA)
        }
        return if (isEdgeToEdgeEnabled) {
            Color.TRANSPARENT
        } else MaterialColors.getColor(context, android.R.attr.navigationBarColor, Color.BLACK)
    }

    companion object {
        private const val EDGE_TO_EDGE_BAR_ALPHA = 128

        private const val EDGE_TO_EDGE_FLAGS = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    }
}