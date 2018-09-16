package com.ekocaman.app.githubbrowser.domain.usecase

import com.ekocaman.app.githubbrowser.domain.SchedulerProvider
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel
import com.ekocaman.app.githubbrowser.domain.repository.GithubRepository
import io.reactivex.Flowable
import javax.inject.Inject

class SearchRepositoryUseCase @Inject constructor(
        schedulerProvider: SchedulerProvider,
        private val githubRepository: GithubRepository
) : FlowableUseCase<SearchRepositoryUseCase.Param, List<RepositoryModel>>(schedulerProvider) {

    public override fun buildUseCaseFlowable(param: Param): Flowable<List<RepositoryModel>> {
        return githubRepository.searchRepositories(param.query)
                .map {
                    item -> item.map {
                        i -> RepositoryModel(i)
                    }
                }
    }

    class Param(val query: String)
}