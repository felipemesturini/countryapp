package com.felipe.labs.countryapi.ws

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.Executor

private const val TAG = "CountryApi"
object CountryApi {
    private fun build(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://restcountries.eu/rest/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun buildMock(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://run.mocky.io/v3/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun countryService(): CountryService {
        val retrofit = build()
        return retrofit.create(CountryService::class.java)
    }
}