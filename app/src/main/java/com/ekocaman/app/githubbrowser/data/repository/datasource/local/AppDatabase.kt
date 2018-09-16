package com.ekocaman.app.githubbrowser.data.repository.datasource.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import android.content.Context
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.converter.Converters
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.LikeRepositoryEntity
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.SearchRepositoryEntity

@Database(entities = [SearchRepositoryEntity::class, LikeRepositoryEntity::class], version = 3, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun likeRepositoryDao(): LikeRepositoryDao
    abstract fun searchRepoDao(): SearchRepoDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
                INSTANCE
                        ?: synchronized(this) {
                            INSTANCE
                                    ?: buildDatabase(context).also { INSTANCE = it }
                        }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        AppDatabase::class.java, "github.db")
                        .fallbackToDestructiveMigration()
                        .build()
    }

}

