package com.felipe.labs.countryapi.web

import com.felipe.labs.countryapi.models.Country

class CountryResponse : ArrayList<CountryResponseItem>()

fun CountryResponse.asCountryList(): List<Country> {
    return this.map {
        Country(
            0,
            it.name,
            it.capital,
            it.flag,
            it.population.toLong()
        )
    }
}