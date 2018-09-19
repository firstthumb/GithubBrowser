package com.ekocaman.app.githubbrowser.di.modules

import com.ekocaman.app.githubbrowser.di.scopes.FragmentScope
import com.ekocaman.app.githubbrowser.ui.home.HomeFragment
import com.ekocaman.app.githubbrowser.ui.home.like.LikeFragment
import com.ekocaman.app.githubbrowser.ui.home.search.SearchFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeSearchFragment(): SearchFragment

    @ContributesAndroidInjector
    @FragmentScope
    abstract fun contributeLikeFragment(): LikeFragment
}