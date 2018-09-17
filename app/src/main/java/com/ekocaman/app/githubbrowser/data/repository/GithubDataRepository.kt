package com.ekocaman.app.githubbrowser.data.repository

import android.arch.persistence.room.EmptyResultSetException
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.GithubLocalDataSource
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.SearchRepository
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.GithubRemoteDataSource
import com.ekocaman.app.githubbrowser.domain.repository.GithubRepository
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class GithubDataRepository @Inject constructor(
        private val localDataSource: GithubLocalDataSource,
        private val remoteDataSource: GithubRemoteDataSource) : GithubRepository {

    override fun getUserRepos(username: String): Flowable<List<Repository>> {
        // TODO: Implement
        return Flowable.create({
            it.onNext(listOf())
        }, BackpressureStrategy.DROP)
    }

    override fun searchRepositories(query: String): Flowable<List<Repository>> {
        return localDataSource.getLikedRepositories()
                .flatMap { likedRepositories ->
                    val idsLiked = likedRepositories.map { item -> item.id }
                    Timber.v("Liked Repositories => $likedRepositories")
                    return@flatMap Flowable.mergeDelayError(
                            localDataSource.searchRepositories(query),
                            remoteDataSource.searchRepositories(query)
                                    .doOnNext { entity ->
                                        saveSearch(entity)
                                    })
                            .map { searchRepository ->
                                searchRepository.items
                                        .map {
                                            Repository(
                                                    it.id,
                                                    it.name,
                                                    it.description,
                                                    it.starCount,
                                                    idsLiked.contains(it.id)
                                            )
                                        }
                            }
                            .distinct()
                            .doOnNext {
                                Timber.v("Fetched Repositories => $it")
                            }
                }
    }

    override fun likeRepository(repository: Repository): Single<Repository> {
        return Single.create<Repository> {
            localDataSource.likeRepository(repository)
            it.onSuccess(repository)
        }
    }

    override fun unlikeRepository(repository: Repository): Single<Repository> {
        return Single.create<Repository> {
            localDataSource.unlikeRepository(repository)
            it.onSuccess(repository)
        }
    }

    override fun toggleLikeRepository(repository: Repository): Single<Repository> {
        return Single.create<Repository> {
            localDataSource.findLikedRepositoryById(repository.id)
                    .subscribe(
                            {
                                localDataSource.unlikeRepository(it)
                            },
                            {
                                when (it) {
                                    is EmptyResultSetException -> localDataSource.likeRepository(repository)
                                    else -> Timber.e(it.localizedMessage)
                                }
                            }
                    )
        }
    }

    private fun saveSearch(entity: SearchRepository) {
        localDataSource.save(entity)
        Timber.v("Search Repository is saved to local => $entity")
    }

    override fun getLikedRepositories(): Flowable<List<Repository>> {
        return localDataSource.getLikedRepositories()
    }
}