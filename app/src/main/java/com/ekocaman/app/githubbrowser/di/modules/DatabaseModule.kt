package com.ekocaman.app.githubbrowser.di.modules

import android.content.Context
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.AppDatabase
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.LikeRepositoryDao
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.SearchRepoDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideSearchRepositoryDao(context: Context): SearchRepoDao {
        val database = AppDatabase.getInstance(context)
        return database.searchRepoDao()
    }

    @Provides
    @Singleton
    fun provideLikeRepositoryDao(context: Context): LikeRepositoryDao {
        val database = AppDatabase.getInstance(context)
        return database.likeRepositoryDao()
    }
}