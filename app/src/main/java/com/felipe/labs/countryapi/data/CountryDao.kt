package com.felipe.labs.countryapi.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.felipe.labs.countryapi.models.Country

@Dao
interface CountryDao {
    @Query(value = "select * from tb_countries order by name")
    fun list() : List<Country>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(items: List<Country>)
}