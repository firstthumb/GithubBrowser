package com.ekocaman.app.githubbrowser.di.components

import com.ekocaman.app.githubbrowser.di.modules.FragmentModule
import com.ekocaman.app.githubbrowser.di.scopes.FragmentScope
import com.ekocaman.app.githubbrowser.ui.home.HomeFragment
import com.ekocaman.app.githubbrowser.ui.home.like.LikeFragment
import com.ekocaman.app.githubbrowser.ui.home.search.SearchFragment
import dagger.Subcomponent

@FragmentScope
@Subcomponent(modules = [(FragmentModule::class)])
interface FragmentComponent {
    fun inject(fragment: SearchFragment)
    fun inject(fragment: LikeFragment)
    fun inject(fragment: HomeFragment)
}
