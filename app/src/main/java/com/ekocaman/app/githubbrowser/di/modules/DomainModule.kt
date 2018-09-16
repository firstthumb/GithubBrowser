package com.ekocaman.app.githubbrowser.di.modules


import com.ekocaman.app.githubbrowser.data.repository.GithubDataRepository
import com.ekocaman.app.githubbrowser.domain.AppSchedulerProvider
import com.ekocaman.app.githubbrowser.domain.SchedulerProvider
import com.ekocaman.app.githubbrowser.domain.repository.GithubRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DomainModule {

    @Provides
    @Singleton
    fun provideSchedulerProvider(): SchedulerProvider {
        return AppSchedulerProvider()
    }

    @Provides
    @Singleton
    fun provideGithubRepository(githubDataRepository: GithubDataRepository): GithubRepository{
        return githubDataRepository
    }
}