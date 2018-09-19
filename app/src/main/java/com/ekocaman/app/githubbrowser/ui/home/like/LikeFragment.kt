package com.ekocaman.app.githubbrowser.ui.home.like

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ekocaman.app.githubbrowser.AppExecutors
import com.ekocaman.app.githubbrowser.databinding.FragmentLikeBinding
import com.ekocaman.app.githubbrowser.ui.base.BaseFragment
import com.ekocaman.app.githubbrowser.ui.base.Result
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject

class LikeFragment : BaseFragment() {

    private lateinit var binding: FragmentLikeBinding

    lateinit var viewModel: LikeViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    private var adapter: LikeRecyclerViewAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentLikeBinding.inflate(inflater, container, false)
        viewModel = ViewModelProviders.of(this, factory).get(LikeViewModel::class.java)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvRepository.layoutManager = LinearLayoutManager(context)
        if (adapter == null) {
            adapter = LikeRecyclerViewAdapter(appExecutors, viewModel)
        }
        rvRepository.adapter = adapter

        viewModel.loadLikedRepositories()
        viewModel.result.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    adapter?.submitList(it.data)
                }
            }
        })
    }

    companion object {
        fun newInstance() = LikeFragment()
    }
}
