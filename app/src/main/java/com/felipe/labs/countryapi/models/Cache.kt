package com.felipe.labs.countryapi.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

const val CURRENT_CACHE_ID = 1L

@Entity(tableName = "tb_cache")
data class Cache(
    @PrimaryKey(autoGenerate = false) val id: Long,
    @ColumnInfo(name = "last_update") val lastCheck: Long) {
    val lastTimeCheck: Date
        get() {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = lastCheck
            return calendar.time
        }
}
