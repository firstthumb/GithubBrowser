package com.ekocaman.app.githubbrowser.data.repository.datasource.model

import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.SearchRepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity.SearchRepositoryResponse

data class SearchRepository(
        val totalCount: Int,
        val incompleteResults: Boolean,
        val items: List<Repository>,

        val query: String,
        val sort: String,
        val order: String
) {
    constructor(entity: SearchRepositoryResponse, query: String, sort: String, order: String) : this(
            totalCount = entity.totalCount,
            incompleteResults = entity.incompleteResults,
            items = entity.items.map { item -> Repository(item) },
            query = query,
            sort = sort,
            order = order
    )

    constructor(entity: SearchRepositoryEntity) : this(
            totalCount = entity.totalCount,
            incompleteResults = entity.incompleteResults,
            items = entity.items.map { item -> Repository(item) },
            query = entity.query,
            sort = entity.sort,
            order = entity.order
    )
}
