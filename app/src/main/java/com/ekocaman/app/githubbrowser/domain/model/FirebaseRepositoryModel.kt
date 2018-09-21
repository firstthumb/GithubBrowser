package com.ekocaman.app.githubbrowser.domain.model

import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository

data class FirebaseRepositoryModel(
        val repositoryId: Long,
        val name: String
) {

    constructor(repository: Repository) : this(
            repository.id,
            repository.name
    )
}