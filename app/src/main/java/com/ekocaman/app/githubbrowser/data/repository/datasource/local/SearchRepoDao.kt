package com.ekocaman.app.githubbrowser.data.repository.datasource.local

import android.arch.persistence.room.*
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.SearchRepositoryEntity
import io.reactivex.Flowable


@Dao
interface SearchRepoDao {

    @get:Query("SELECT * FROM SearchRepositoryEntity")
    val all: Flowable<List<SearchRepositoryEntity>>

    @Query("SELECT * FROM SearchRepositoryEntity WHERE q = :q AND sort = :sort AND `order` = :order LIMIT 1")
    fun findByQuery(q: String, sort: String, order: String): Flowable<SearchRepositoryEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repoEntity: SearchRepositoryEntity)

    @Query("DELETE FROM SearchRepositoryEntity")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.ROLLBACK)
    fun update(entity: SearchRepositoryEntity)

    @Transaction
    fun updateData(repoEntity: SearchRepositoryEntity) {
        deleteAll()
        insert(repoEntity)
    }

    @Delete
    fun delete(repoEntity: SearchRepositoryEntity)
}
