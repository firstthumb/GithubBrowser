package com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity

import com.google.gson.annotations.SerializedName

data class SearchRepositoryResponse(
        @SerializedName("total_count") val totalCount: Int,
        @SerializedName("incomplete_results") val incompleteResults: Boolean,
        @SerializedName("items") val items: List<RepositoryResponse>
)
