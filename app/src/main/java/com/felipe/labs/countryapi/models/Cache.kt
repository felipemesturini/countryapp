package com.felipe.labs.countryapi.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

const val CURRENT_CACHE_ID = 1

@Entity(tableName = "tb_cache")
data class Cache(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "last_update") val lastChech: String)
