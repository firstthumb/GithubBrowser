package com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.SearchRepository

@Entity(tableName = "SearchRepositoryEntity")
data class SearchRepositoryEntity(
        val totalCount: Int,
        val incompleteResults: Boolean,
        val items: List<RepositoryEntity>,

        @ColumnInfo(name = "q", index = true) val query: String,
        @ColumnInfo(name = "sort", index = true) val sort: String,
        @ColumnInfo(name = "order", index = true) val order: String,

        @PrimaryKey(autoGenerate = true)
        var uid: Long = 0
) {
    constructor(entity: SearchRepository) : this(
            totalCount = entity.totalCount,
            incompleteResults = entity.incompleteResults,
            items = entity.items.map { item -> RepositoryEntity(item) },
            query = entity.query,
            sort = entity.sort,
            order = entity.order,
            uid = 0
    )
}
