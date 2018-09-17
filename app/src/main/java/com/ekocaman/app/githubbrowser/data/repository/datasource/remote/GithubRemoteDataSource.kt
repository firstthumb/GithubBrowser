package com.ekocaman.app.githubbrowser.data.repository.datasource.remote

import com.ekocaman.app.githubbrowser.data.api.GithubApi
import com.ekocaman.app.githubbrowser.data.mapper.RepoMapper
import com.ekocaman.app.githubbrowser.data.repository.datasource.GithubDataStore
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.SearchRepository
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class GithubRemoteDataSource @Inject constructor(
        private val githubApi: GithubApi,
        private val mapper: RepoMapper) : GithubDataStore {

    override fun searchRepositories(query: String, sort: String, order: String): Flowable<SearchRepository> {
        return githubApi.searchRepositories(query, sort, order)
                .map {
                    mapper.transform(it, query, sort, order)
                }
                .toFlowable()
    }

    override fun save(entity: SearchRepository) {
        throw UnsupportedOperationException()
    }

    override fun getLikedRepositories(): Flowable<List<Repository>> {
        throw UnsupportedOperationException()
    }

    override fun likeRepository(entity: Repository) {
        throw UnsupportedOperationException()
    }

    override fun unlikeRepository(entity: Repository) {
        throw UnsupportedOperationException()
    }

    override fun findLikedRepositoryById(id: Long): Single<Repository> {
        throw UnsupportedOperationException()
    }
}