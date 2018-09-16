package com.ekocaman.app.githubbrowser.data.repository

import com.ekocaman.app.githubbrowser.UnitTest
import com.ekocaman.app.githubbrowser.data.api.GithubApi
import com.ekocaman.app.githubbrowser.data.mapper.RepoMapper
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.GithubLocalDataSource
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.GithubRemoteDataSource
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.entity.RepositoryResponse
import com.ekocaman.app.githubbrowser.domain.repository.GithubRepository
import org.junit.Before
import org.mockito.Mock
import org.mockito.Spy

class GithubDataRepositoryTest : UnitTest() {

    private lateinit var githubRepository: GithubRepository

    @Spy
    private lateinit var mapper: RepoMapper

    @Mock
    private lateinit var githubApi: GithubApi

    @Mock
    private lateinit var repositoryResponse: RepositoryResponse

    @Mock
    private lateinit var localDataStore: GithubLocalDataSource

    @Mock
    private lateinit var remoteDataStore: GithubRemoteDataSource

    @Before
    fun setUp() {
        githubRepository = GithubDataRepository(localDataStore, remoteDataStore)
    }
}