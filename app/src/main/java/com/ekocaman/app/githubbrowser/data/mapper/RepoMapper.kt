package com.ekocaman.app.githubbrowser.data.mapper

import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.LikeRepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.SearchRepository
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.RepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.SearchRepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity.SearchRepositoryResponse
import javax.inject.Inject

class RepoMapper @Inject constructor() {

    fun transform(entity: RepositoryEntity): Repository {
        return Repository(entity)
    }

    fun transform(entity: SearchRepositoryResponse,
                  query: String,
                  sort: String,
                  order: String): SearchRepository {
        return SearchRepository(entity, query, sort, order)
    }

    fun transform(entity: SearchRepositoryEntity): SearchRepository {
        return SearchRepository(entity)
    }

    fun transform(entity: SearchRepository): SearchRepositoryEntity {
        return SearchRepositoryEntity(entity)
    }

    fun transform(entity: LikeRepositoryEntity): Repository {
        return Repository(entity)
    }

    fun transform(entity: Repository): LikeRepositoryEntity {
        return LikeRepositoryEntity(entity)
    }
}