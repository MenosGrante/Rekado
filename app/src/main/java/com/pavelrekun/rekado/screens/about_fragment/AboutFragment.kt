package com.pavelrekun.rekado.screens.about_fragment

import android.os.Bundle
import android.view.View
import com.pavelrekun.rekado.BuildConfig
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentAboutBinding
import com.pavelrekun.rekado.services.constants.Links
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.Utils
import dev.chrisbanes.insetter.applyInsetter

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding by viewBinding(FragmentAboutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initVersion()
        initClickListeners()
        initEdgeToEdge()
    }

    private fun initVersion() {
        binding.aboutVersion.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    private fun initClickListeners() {
        binding.aboutLinkGitHubProject.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.GITHUB_PROJECT) }

        binding.aboutDeveloperPortfolio.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.PERSONAL_SITE) }
        binding.aboutDeveloperInstagram.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.INSTAGRAM) }
        binding.aboutDeveloperGithub.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.GITHUB) }
        binding.aboutDeveloperX.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.X) }
    }

    private fun initEdgeToEdge() {
        binding.aboutLayoutContainer.applyInsetter {
            type(navigationBars = true) {
                padding()
            }
        }
    }

}