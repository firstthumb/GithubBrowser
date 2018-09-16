package com.ekocaman.app.githubbrowser.ui.home.like

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel
import com.ekocaman.app.githubbrowser.domain.usecase.FetchLikedRepositoryUseCase
import com.ekocaman.app.githubbrowser.domain.usecase.LikeRepositoryUseCase
import com.ekocaman.app.githubbrowser.domain.usecase.UnlikeRepositoryUseCase
import com.ekocaman.app.githubbrowser.ui.base.Result
import com.ekocaman.app.githubbrowser.ui.common.LikeListener
import com.ekocaman.app.githubbrowser.ui.common.RefreshListener
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class LikeViewModel @Inject constructor(
        private val fetchLikedRepositoryUseCase: FetchLikedRepositoryUseCase,
        private val unlikeRepositoryUseCase: UnlikeRepositoryUseCase): ViewModel(), RefreshListener, LikeListener {
    val isLoading = ObservableBoolean()
    val isError = ObservableBoolean()

    val result: MutableLiveData<Result<List<RepositoryModel>>> = MutableLiveData()

    fun loadLikedRepositories() {
        isLoading.set(true)
        result.value = Result.loading(true)
        fetchLikedRepositoryUseCase.execute(
                Consumer {
                    this.isLoading.set(false)
                    this.isError.set(false)
                    result.value = Result.success(it)
                    Timber.v("Liked view updated")
                },
                Consumer {
                    Timber.e(it.localizedMessage)
                    this.isLoading.set(false)
                    this.isError.set(true)
                    result.value = Result.failure(it)
                },
                FetchLikedRepositoryUseCase.Param()
        )
    }

    override fun onRefresh() {
        loadLikedRepositories()
    }

    override fun onLikeClicked(model: RepositoryModel) {
        Timber.v("Removed from liked repositories : $model")
        unlikeRepositoryUseCase.execute(
                Consumer {
                    Timber.v("Successfully liked the repository")
                },
                Consumer {
                    Timber.e(it.localizedMessage)
                },
                UnlikeRepositoryUseCase.Param(model)
        )

    }

    override fun onCleared() {
        super.onCleared()
        fetchLikedRepositoryUseCase.dispose()
    }
}