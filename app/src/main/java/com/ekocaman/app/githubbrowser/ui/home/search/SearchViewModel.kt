package com.ekocaman.app.githubbrowser.ui.home.search

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel
import com.ekocaman.app.githubbrowser.domain.usecase.SearchRepositoryUseCase
import com.ekocaman.app.githubbrowser.domain.usecase.ToggleLikeRepositoryUseCase
import com.ekocaman.app.githubbrowser.ui.base.Result
import com.ekocaman.app.githubbrowser.ui.common.LikeListener
import com.ekocaman.app.githubbrowser.ui.common.RefreshListener
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class SearchViewModel @Inject constructor(
        private val useCase: SearchRepositoryUseCase,
        private val toggleLikeRepositoryUseCase: ToggleLikeRepositoryUseCase) : ViewModel(), RefreshListener, LikeListener {
    val isLoading = ObservableBoolean()
    val isError = ObservableBoolean()
    val onRefresh = MutableLiveData<Boolean>()

    val outcome: MutableLiveData<Result<List<RepositoryModel>>> = MutableLiveData()

    private lateinit var latestQuery: String

    fun loadRepositories(query: String = "elixir") {
        latestQuery = query
        isLoading.set(true)
        outcome.value = Result.loading(true)
        useCase.execute(
                Consumer {
                    this.isLoading.set(false)
                    this.isError.set(false)
                    outcome.value = Result.success(it)
                },
                Consumer {
                    Timber.e(it.localizedMessage)
                    this.isLoading.set(false)
                    this.isError.set(true)
                    outcome.value = Result.failure(it)
                },
                SearchRepositoryUseCase.Param(query)
        )
    }

    override fun onLikeClicked(model: RepositoryModel) {
        Timber.v("Liked repository : $model")
        toggleLikeRepositoryUseCase.execute(
                Consumer {
                    Timber.v("Successfully toggled the repository")
                },
                Consumer {
                    Timber.e(it.localizedMessage)
                },
                ToggleLikeRepositoryUseCase.Param(model)
        )
    }

    override fun onRefresh() {
//        onRefresh.value = true
//        loadRepositories(latestQuery)
    }

    override fun onCleared() {
        super.onCleared()
        useCase.dispose()
    }
}