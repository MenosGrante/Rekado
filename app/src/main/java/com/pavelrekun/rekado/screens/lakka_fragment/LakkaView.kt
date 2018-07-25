package com.pavelrekun.rekado.screens.lakka_fragment

import android.support.v4.content.ContextCompat
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.lakka.LakkaHelper
import kotlinx.android.synthetic.main.fragment_lakka.*

class LakkaView(private val activity: BaseActivity) : LakkaContract.View {

    override fun initViews() {
        activity.setTitle(R.string.navigation_lakka)

        initCBFSCategory()
        initCorebootCategory()
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

        activity.lakkaCBFSDate.text = activity.getString(R.string.lakka_last_update, LakkaHelper.PAYLOAD_UPDATE_DATE)
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

        activity.lakkaCorebootDate.text = activity.getString(R.string.lakka_last_update, LakkaHelper.COREBOOT_UPDATE_DATE)
    }
}