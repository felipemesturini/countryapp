package com.felipe.labs.countryapi.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_countries")
data class Country(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val name: String,
    val capital: String,
    val imagem: String,
    val population: Long
)
