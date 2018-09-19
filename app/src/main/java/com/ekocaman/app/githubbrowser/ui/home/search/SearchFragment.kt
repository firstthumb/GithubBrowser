package com.ekocaman.app.githubbrowser.ui.home.search

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekocaman.app.githubbrowser.AppExecutors
import com.ekocaman.app.githubbrowser.databinding.FragmentSearchBinding
import com.ekocaman.app.githubbrowser.ui.base.BaseFragment
import com.ekocaman.app.githubbrowser.ui.base.Result
import com.ekocaman.app.githubbrowser.ui.home.HomeViewModel
import kotlinx.android.synthetic.main.fragment_search.*
import timber.log.Timber
import javax.inject.Inject

class SearchFragment : BaseFragment() {
    private lateinit var binding: FragmentSearchBinding

    lateinit var viewModel: SearchViewModel
    lateinit var homeViewModel: HomeViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var adapter: SearchRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(SearchViewModel::class.java)
        activity?.let {
            homeViewModel = ViewModelProviders.of(it, factory).get(HomeViewModel::class.java)
        }
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvRepository.layoutManager = LinearLayoutManager(context)
        if (adapter == null) {
            adapter = SearchRecyclerViewAdapter(appExecutors, viewModel)
        }
        rvRepository.adapter = adapter
        viewModel.loadRepositories()
        viewModel.outcome.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    adapter?.submitList(it.data)
                }
            }
        })
        homeViewModel.search.observe(this, Observer {
            Timber.v("Search notification got : $it")
            it?.let {
                viewModel.loadRepositories(it)
            }
        })
    }

    companion object {
        fun newInstance() = SearchFragment()
    }
}
