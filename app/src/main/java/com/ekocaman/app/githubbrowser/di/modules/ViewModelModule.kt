package com.ekocaman.app.githubbrowser.di.modules

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.ekocaman.app.githubbrowser.di.ViewModelFactory
import com.ekocaman.app.githubbrowser.di.ViewModelKey
import com.ekocaman.app.githubbrowser.ui.home.HomeViewModel
import com.ekocaman.app.githubbrowser.ui.home.like.LikeViewModel
import com.ekocaman.app.githubbrowser.ui.home.search.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    abstract fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LikeViewModel::class)
    abstract fun bindLikeViewModel(viewModel: LikeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel
}