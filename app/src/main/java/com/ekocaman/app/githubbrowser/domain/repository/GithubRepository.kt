package com.ekocaman.app.githubbrowser.domain.repository

import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import io.reactivex.Flowable
import io.reactivex.Single

interface GithubRepository {
    fun getUserRepos(username: String): Flowable<List<Repository>>

    fun searchRepositories(query: String): Flowable<List<Repository>>

    fun getLikedRepositories(): Flowable<List<Repository>>

    fun likeRepository(repository: Repository): Single<Repository>

    fun unlikeRepository(repository: Repository): Single<Repository>

    fun toggleLikeRepository(repository: Repository): Single<Repository>
}