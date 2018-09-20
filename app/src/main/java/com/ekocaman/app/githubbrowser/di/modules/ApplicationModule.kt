package com.ekocaman.app.githubbrowser.di.modules

import android.content.Context
import com.ekocaman.app.githubbrowser.GithubApp
import com.ekocaman.app.githubbrowser.domain.service.FirebaseService
import com.ekocaman.app.githubbrowser.domain.service.FirebaseServiceImpl
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: GithubApp): Context

    @Binds
    abstract fun provideFirebaseService(service: FirebaseServiceImpl): FirebaseService
}