package com.ekocaman.app.githubbrowser.domain.usecase

import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import com.ekocaman.app.githubbrowser.domain.SchedulerProvider
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel
import com.ekocaman.app.githubbrowser.domain.repository.GithubRepository
import io.reactivex.Single
import javax.inject.Inject

class LikeRepositoryUseCase @Inject constructor(
        schedulerProvider: SchedulerProvider,
        private val githubRepository: GithubRepository
) : SequentialUseCase<LikeRepositoryUseCase.Param, RepositoryModel>(schedulerProvider) {

    public override fun buildUseCaseSingle(param: Param): Single<RepositoryModel> {
        return githubRepository.likeRepository(Repository(param.repository))
                .map { item ->
                    RepositoryModel(item)
                }
    }

    class Param(val repository: RepositoryModel)
}