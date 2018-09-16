package com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.ekocaman.app.githubbrowser.data.repository.datasource.model.Repository

@Entity(tableName = "LikeRepositoryEntity")
data class LikeRepositoryEntity(
        @PrimaryKey(autoGenerate = false) val id: Long,
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