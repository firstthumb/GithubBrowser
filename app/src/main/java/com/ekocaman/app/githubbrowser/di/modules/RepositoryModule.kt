package com.ekocaman.app.githubbrowser.di.modules

import com.ekocaman.app.githubbrowser.data.repository.datasource.GithubDataStore
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.GithubLocalDataSource
import com.ekocaman.app.githubbrowser.data.repository.datasource.remote.GithubRemoteDataSource
import com.ekocaman.app.githubbrowser.di.Local
import com.ekocaman.app.githubbrowser.di.Remote
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    @Local
    fun provideLocalDataSource(localDataSource: GithubLocalDataSource): GithubDataStore {
        return localDataSource
    }


    @Provides
    @Singleton
    @Remote
    fun provideRemoteDataSource(remoteDataSource: GithubRemoteDataSource): GithubDataStore {
        return remoteDataSource
    }
}