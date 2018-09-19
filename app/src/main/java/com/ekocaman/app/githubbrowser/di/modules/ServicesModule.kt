package com.ekocaman.app.githubbrowser.di.modules

import com.ekocaman.app.githubbrowser.GithubFirebaseMessagingService
import com.ekocaman.app.githubbrowser.di.scopes.ApplicationScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ServicesModule {

    @ContributesAndroidInjector
    @ApplicationScope
    abstract fun contributeService(): GithubFirebaseMessagingService
}