package com.ekocaman.app.githubbrowser.data.repository.datasource.local.converter

import android.arch.persistence.room.TypeConverter
import com.ekocaman.app.githubbrowser.data.repository.datasource.local.entity.RepositoryEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class Converters {
    private val type = object : TypeToken<List<RepositoryEntity>>() {}.type
    private val gson = Gson()

    @TypeConverter
    fun fromListRepositoryEntity(value: List<RepositoryEntity>): String {
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toListRepositoryEntity(value: String?): List<RepositoryEntity> {
        if (value == null) {
            return Collections.emptyList()
        }

        return gson.fromJson(value, type)
    }
}