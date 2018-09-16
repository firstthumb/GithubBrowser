package com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity

import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository

data class RepositoryEntity(
        val id: Long,
        val name: String,
        val description: String,
        val starCount: Int
) {
    constructor(item: Repository) : this(
            id = item.id,
            name = item.name,
            description = item.description,
            starCount = item.starCount
    )
}