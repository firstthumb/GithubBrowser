package com.ekocaman.app.githubbrowser.domain.model

import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository


data class RepositoryModel(
        val id: Long,
        val name: String = "",
        val description: String = "",
        val starCount: Int = 0,
        val liked: Boolean = false
) {
    constructor(entity: Repository) : this(
            entity.id,
            entity.name,
            entity.description,
            entity.starCount,
            entity.liked
    )
}