package com.ekocaman.app.githubbrowser.di.modules

import com.ekocaman.app.githubbrowser.GithubFirebaseMessagingService
import com.ekocaman.app.githubbrowser.di.scopes.ApplicationScope
import com.ekocaman.app.githubbrowser.domain.job.FirebaseJobService
import com.ekocaman.app.githubbrowser.domain.job.GithubRefreshJobService
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServicesModule {

    @ContributesAndroidInjector
    @ApplicationScope
    abstract fun contributeService(): GithubFirebaseMessagingService

    @ContributesAndroidInjector
    @ApplicationScope
    abstract fun contributeFirebaseJobService(): FirebaseJobService

    @ContributesAndroidInjector
    @ApplicationScope
    abstract fun contributeGithubRefreshJobService(): GithubRefreshJobService
}