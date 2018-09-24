package com.ekocaman.app.githubbrowser.ui.home.like

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import com.ekocaman.app.githubbrowser.domain.job.GithubRefreshJobService
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel
import com.ekocaman.app.githubbrowser.domain.usecase.FetchLikedRepositoryUseCase
import com.ekocaman.app.githubbrowser.domain.usecase.UnlikeRepositoryUseCase
import com.ekocaman.app.githubbrowser.ui.base.BaseViewModel
import com.ekocaman.app.githubbrowser.ui.base.Result
import com.ekocaman.app.githubbrowser.ui.common.LikeListener
import com.ekocaman.app.githubbrowser.ui.common.RefreshListener
import com.firebase.jobdispatcher.Constraint
import com.firebase.jobdispatcher.FirebaseJobDispatcher
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject

class LikeViewModel @Inject constructor(
        private val dispatcher: FirebaseJobDispatcher,
        private val fetchLikedRepositoryUseCase: FetchLikedRepositoryUseCase,
        private val unlikeRepositoryUseCase: UnlikeRepositoryUseCase) : BaseViewModel(), RefreshListener, LikeListener {
    val isLoading = ObservableBoolean()
    val isError = ObservableBoolean()

    val result: MutableLiveData<Result<List<RepositoryModel>>> = MutableLiveData()

    init {
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
                }
        )
        addDisposable(fetchLikedRepositoryUseCase)
    }

    fun loadLikedRepositories() {
        isLoading.set(true)
        result.value = Result.loading(true)
        fetchLikedRepositoryUseCase.sendEvent(FetchLikedRepositoryUseCase.Param())
    }

    override fun onRefresh() {
        // TODO: Implement Me
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
        addDisposable(unlikeRepositoryUseCase)

        // TODO: Replace with RxJava
        createSyncDataJob()
    }

    private fun createSyncDataJob() {
        val syncJob = dispatcher.newJobBuilder()
                .setService(GithubRefreshJobService::class.java)
                .setTag("sync-data-job")
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .build()
        val result = dispatcher.schedule(syncJob)
        if (result == FirebaseJobDispatcher.SCHEDULE_RESULT_SUCCESS) {
            Timber.v("Sync Job is scheduler successfully")
        }
    }
}