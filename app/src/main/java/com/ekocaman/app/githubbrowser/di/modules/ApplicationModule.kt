package com.ekocaman.app.githubbrowser.di.modules

import android.content.Context
import com.ekocaman.app.githubbrowser.GithubApp
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {

    @Binds
    abstract fun bindContext(application: GithubApp): Context
}