package com.felipe.labs.countryapi.ws

import com.felipe.labs.countryapi.web.CountryResponse
import com.felipe.labs.countryapi.web.CountryResponseItem
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface CountryService {
    @GET(value = "all")
    suspend fun listResponse(): List<CountryResponseItem>

    @GET(value = "all")
    fun listCall(): Call<CountryResponse>

    @GET(value = "f8ac40fa-22ed-4f53-b01d-2da842af7504")
    fun mockTest() : Response<CountryResponseItem>
}