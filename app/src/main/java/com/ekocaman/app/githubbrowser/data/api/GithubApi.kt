package com.ekocaman.app.githubbrowser.data.api

import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity.RepositoryResponse
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity.SearchRepositoryResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {

    @GET("/users/{username}/repos")
    fun getUserRepos(@Query("username") username: String): Single<List<RepositoryResponse>>

    @GET("/search/repositories")
    fun searchRepositories(@Query("q") query: String,
                           @Query("sort") sort: String = "stars",   //  stars / forks / updated
                           @Query("order") order: String = "desc")  //  asc / desc
            : Single<SearchRepositoryResponse>


}