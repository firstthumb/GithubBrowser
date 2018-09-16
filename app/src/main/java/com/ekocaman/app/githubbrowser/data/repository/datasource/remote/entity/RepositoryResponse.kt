package com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class RepositoryResponse (
        @SerializedName("id") val id: Long,
        @SerializedName("name") val name: String,
        @SerializedName("description") val description: String,
        @SerializedName("stargazers_count") val starCount: Int
)