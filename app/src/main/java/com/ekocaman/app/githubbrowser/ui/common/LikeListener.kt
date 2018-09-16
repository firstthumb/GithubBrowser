package com.ekocaman.app.githubbrowser.ui.common

import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel

interface LikeListener {
    fun onLikeClicked(model: RepositoryModel)
}