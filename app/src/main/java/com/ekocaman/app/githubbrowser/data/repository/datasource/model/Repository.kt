package com.ekocaman.app.githubbrowser.data.repository.datasource.model

import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.LikeRepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.RepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity.RepositoryResponse
import com.ekocaman.app.githubbrowser.domain.model.RepositoryModel

data class Repository(
        val id: Long,
        val name: String,
        val description: String,
        val starCount: Int,
        val liked: Boolean = false
) {
    constructor(entity: RepositoryResponse) : this(
            entity.id,
            entity.name,
            entity.description,
            entity.starCount
    )

    constructor(entity: RepositoryEntity) : this(
            entity.id,
            entity.name,
            entity.description,
            entity.starCount
    )

    constructor(entity: LikeRepositoryEntity) : this(
            entity.id,
            entity.name,
            entity.description,
            entity.starCount,
            true
    )

    constructor(model: RepositoryModel) : this(
            model.id,
            model.name,
            model.description,
            model.starCount,
            model.liked
    )
}