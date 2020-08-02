package com.pavelrekun.rekado.screens.about_fragment

import android.os.Bundle
import android.view.View
import com.pavelrekun.magta.constants.Links
import com.pavelrekun.rekado.BuildConfig
import com.pavelrekun.rekado.R
import com.pavelrekun.rekado.base.BaseFragment
import com.pavelrekun.rekado.databinding.FragmentAboutBinding
import com.pavelrekun.rekado.services.Constants
import com.pavelrekun.rekado.services.extensions.viewBinding
import com.pavelrekun.rekado.services.utils.Utils
import de.halfbit.edgetoedge.Edge
import de.halfbit.edgetoedge.edgeToEdge

class AboutFragment : BaseFragment(R.layout.fragment_about) {

    private val binding by viewBinding(FragmentAboutBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScrollingBehaviour(binding.aboutLayoutScroll)
        initVersion()
        initClickListeners()
        initEdgeToEdge()
    }

    private fun initVersion() {
        binding.aboutVersion.text = "${BuildConfig.VERSION_NAME} (${BuildConfig.VERSION_CODE})"
    }

    private fun initClickListeners() {
        binding.aboutLinkGitHubProject.setOnClickListener { Utils.openLink(requireBaseActivity(), Constants.GITHUB_PROJECT_LINK) }

        binding.aboutDeveloperPortfolio.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.PERSONAL_SITE_LINK) }
        binding.aboutDeveloperInstagram.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.INSTAGRAM_DRONE_LINK) }
        binding.aboutDeveloperGithub.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.GITHUB_LINK) }
        binding.aboutDeveloperTwitter.setOnClickListener { Utils.openLink(requireBaseActivity(), Links.TWITTER_LINK) }
    }

    private fun initEdgeToEdge() {
        edgeToEdge {
            binding.aboutLayoutContainer.fit { Edge.Bottom }
        }
    }

}