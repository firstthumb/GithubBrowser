package com.ekocaman.app.githubbrowser.data.repository.datasource.local

import android.arch.persistence.room.*
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.LikeRepositoryEntity
import io.reactivex.Flowable
import io.reactivex.Single


@Dao
interface LikeRepositoryDao {

    @get:Query("SELECT * FROM LikeRepositoryEntity")
    val all: Flowable<List<LikeRepositoryEntity>>

    @Query("SELECT * FROM LikeRepositoryEntity WHERE id = :id")
    fun findById(id: Long): Single<LikeRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: LikeRepositoryEntity)

    @Query("DELETE FROM LikeRepositoryEntity")
    fun deleteAll()

    @Delete
    fun delete(entity: LikeRepositoryEntity)
}
