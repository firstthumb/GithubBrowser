package com.ekocaman.app.githubbrowser.data.repository.datasource.local

import com.ekocaman.app.githubbrowser.data.mapper.RepoMapper
import com.ekocaman.app.githubbrowser.data.repository.datasource.GithubDataStore
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.SearchRepository
import io.reactivex.Flowable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class GithubLocalDataSource @Inject constructor(
        private val searchRepoDao: SearchRepoDao,
        private val likeRepositoryDao: LikeRepositoryDao,
        private val mapper: RepoMapper) : GithubDataStore {

    override fun searchRepositories(query: String, sort: String, order: String): Flowable<SearchRepository> {
        return searchRepoDao.findByQuery(query, sort, order)
                .map {
                    mapper.transform(it)
                }
    }

    override fun save(entity: SearchRepository) {
        searchRepoDao.findByQuery(entity.query, entity.sort, entity.order)
                .first(mapper.transform(entity))
                .subscribe({
                    val freshSearch = mapper.transform(entity)
                    freshSearch.uid = it.uid
                    searchRepoDao.insert(freshSearch)
                }, {
                    Timber.e(it)
                })
    }

    override fun getLikedRepositories(): Flowable<List<Repository>> {
        return likeRepositoryDao.all
                .map { item ->
                    item.map(mapper::transform)
                }
    }

    override fun likeRepository(entity: Repository) {
        likeRepositoryDao.insert(mapper.transform(entity))
    }

    override fun unlikeRepository(entity: Repository) {
        likeRepositoryDao.delete(mapper.transform(entity))
    }

    override fun findLikedRepositoryById(id: Long): Single<Repository> {
        return likeRepositoryDao.findById(id).map(mapper::transform)
    }
}
