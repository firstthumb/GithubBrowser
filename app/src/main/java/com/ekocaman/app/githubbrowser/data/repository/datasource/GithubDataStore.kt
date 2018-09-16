package com.ekocaman.app.githubbrowser.data.repository.datasource

import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.SearchRepository
import io.reactivex.Flowable
import io.reactivex.Single

interface GithubDataStore {
    fun searchRepositories(query: String, sort: String = "stars", order: String = "desc"): Flowable<SearchRepository>

    fun save(entity: SearchRepository)

    fun findLikedRepositoryById(id: Long): Single<Repository>

    fun getLikedRepositories(): Flowable<List<Repository>>

    fun likeRepository(entity: Repository)

    fun unlikeRepository(entity: Repository)
}