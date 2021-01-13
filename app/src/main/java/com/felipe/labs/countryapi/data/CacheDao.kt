package com.felipe.labs.countryapi.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felipe.labs.countryapi.models.CURRENT_CACHE_ID
import com.felipe.labs.countryapi.models.Cache

@Dao
interface CacheDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: Cache)

    @Query(value = "select * from tb_cache where id = $CURRENT_CACHE_ID")
    fun get(): Cache?
}