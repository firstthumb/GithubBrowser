package com.ekocaman.app.githubbrowser.ui.home


import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekocaman.app.githubbrowser.databinding.FragmentHomeBinding
import com.ekocaman.app.githubbrowser.ui.base.BaseFragment
import com.ekocaman.app.githubbrowser.ui.home.adapter.HomePagerAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


class HomeFragment : BaseFragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeAdapter: HomePagerAdapter

    lateinit var viewModel: HomeViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        component.inject(this)
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        activity?.let {
            viewModel = ViewModelProviders.of(it, factory).get(HomeViewModel::class.java)
        }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupViewPager()
    }

    private fun setupViewPager() {
        homeAdapter = HomePagerAdapter(childFragmentManager)
        vpHome.adapter = homeAdapter
        tabLayout.setupWithViewPager(vpHome)
    }

    companion object {
        fun newInstance(): HomeFragment {
            return HomeFragment()
        }
    }
}
