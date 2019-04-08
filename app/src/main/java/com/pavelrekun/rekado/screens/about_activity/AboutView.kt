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
        activity.aboutLinkGitHubProject.setOnClickListener { Utils.openLink(activity, Constants.GITHUB_PROJECT_LINK) }
        activity.aboutDeveloperPersonalSite.setOnClickListener { Utils.openLink(activity, Constants.PERSONAL_SITE_LINK) }
        activity.aboutDeveloperGitHub.setOnClickListener { Utils.openLink(activity, Constants.GITHUB_PROFILE_LINK) }
        activity.aboutDeveloperTwitter.setOnClickListener { Utils.openLink(activity, Constants.TWITTER_LINK) }
    }

}