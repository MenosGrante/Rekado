package com.pavelrekun.rekado.screens.about_fragment

import android.os.Bundle
import android.view.View
import com.pavelrekun.rekado.BuildConfig
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentAboutBinding
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.Utils

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding by viewBinding(FragmentAboutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScrollingBehaviour(binding.aboutLayoutScroll)
        initVersion()
        initClickListeners()
    }

    private fun initVersion() {
        binding.aboutVersion.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    private fun initClickListeners() {
        binding.aboutLinkGitHubProject.setOnClickListener { Utils.openLink(getBaseActivity(), Constants.GITHUB_PROJECT_LINK) }
        binding.aboutDeveloperPersonalSite.setOnClickListener { Utils.openLink(getBaseActivity(), Constants.PERSONAL_SITE_LINK) }
        binding.aboutDeveloperGitHub.setOnClickListener { Utils.openLink(getBaseActivity(), Constants.GITHUB_PROFILE_LINK) }
        binding.aboutDeveloperTwitter.setOnClickListener { Utils.openLink(getBaseActivity(), Constants.TWITTER_LINK) }
    }

}