package com.ekocaman.app.githubbrowser.di.modules

import com.ekocaman.app.githubbrowser.di.scopes.ActivityScope
import com.ekocaman.app.githubbrowser.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {


    @ContributesAndroidInjector(modules = [FragmentModule::class])
    @ActivityScope
    abstract fun contributeMainActivity(): MainActivity

}
