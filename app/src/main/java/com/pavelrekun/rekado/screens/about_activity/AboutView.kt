package com.pavelrekun.rekado.screens.about_activity

import com.pavelrekun.rekado.BuildConfig
import com.pavelrekun.rekado.base.BaseActivity
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.utils.Utils
import kotlinx.android.synthetic.main.activity_about.*

class AboutView(private val activity: BaseActivity) : AboutContract.View {

    init {
        initViews()
    }

    override fun initViews() {
        initToolbar()
        initClickListeners()
        initVersion()
    }

    override fun initToolbar() {
        activity.setSupportActionBar(activity.aboutToolbar)
        activity.aboutToolbar.setNavigationOnClickListener { activity.onBackPressed() }
    }

    override fun initVersion() {
        activity.aboutVersion.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    override fun initClickListeners() {
        activity.aboutLinkGitHubProject.setOnClickListener { Utils.openLink(Constants.GITHUB_PROJECT_LINK) }
        activity.aboutDeveloperGitHub.setOnClickListener { Utils.openLink(Constants.GITHUB_PROFILE_LINK) }
        activity.aboutDeveloperTwitter.setOnClickListener { Utils.openLink(Constants.TWITTER_LINK) }
    }

}