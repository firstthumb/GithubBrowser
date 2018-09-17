package com.ekocaman.app.githubbrowser.ui.home.search

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ekocaman.app.githubbrowser.AppExecutors
import com.ekocaman.app.githubbrowser.BR
import com.ekocaman.app.githubbrowser.R
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel
import com.ekocaman.app.githubbrowser.ui.base.DataBoundListAdapter
import com.ekocaman.app.githubbrowser.ui.common.LikeListener
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.item_home.view.*

class SearchRecyclerViewAdapter(
        val appExecutors: AppExecutors,
        val likeListener: LikeListener
) : DataBoundListAdapter<RepositoryModel, ViewDataBinding>(
        appExecutors,
        diffCallback = object : DiffUtil.ItemCallback<RepositoryModel>() {
            override fun areContentsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: RepositoryModel, newItem: RepositoryModel): Boolean {
                return oldItem == newItem
            }

        }
) {
    override fun createBinding(parent: ViewGroup, viewType: Int): ViewDataBinding {
        val layoutInflater = LayoutInflater.from(parent.context)
        val layoutType = R.layout.item_home

        return DataBindingUtil.inflate(layoutInflater, layoutType, parent, false)
    }

    override fun bind(binding: ViewDataBinding, item: RepositoryModel) {
        binding.setVariable(BR.repositoryModel, item)
        binding.setVariable(BR.likeListener, likeListener)

        binding.root.like.isLiked = item.liked
        binding.root.like.setOnLikeListener(object : OnLikeListener {
            override fun liked(p0: LikeButton?) {
                likeListener.onLikeClicked(item)
            }

            override fun unLiked(p0: LikeButton?) {
                likeListener.onLikeClicked(item)
            }
        })
    }

    override fun getItemViewType(position: Int): Int {
        return Type.ITEM.value
    }

    enum class Type(val value: Int) {
        HEADER(0),
        ITEM(1)
    }
}