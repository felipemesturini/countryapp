package com.felipe.labs.countryapi.repository

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.felipe.labs.countryapi.R
import com.felipe.labs.countryapi.data.DbCountry
import com.felipe.labs.countryapi.models.Country
import com.felipe.labs.countryapi.web.asCountryList
import com.felipe.labs.countryapi.ws.CountryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await


private const val TAG = "CountryRepository"

class CountryRepository(private val db: DbCountry) {
    val _items = MutableLiveData<List<Country>>()

    val countries: LiveData<List<Country>>
        get() = _items

    suspend fun fetchData() {
        withContext(Dispatchers.IO) {
            Log.i(TAG, "Find videos Data")
            var itens = db.countryDao.list()
            if (itens.isEmpty()) {
                val service = CountryApi.countryService()
                val response = service.listCall()
                val body = response.await()
                itens = body.asCountryList()
                db.countryDao.insert(itens)

            }
            _items.postValue(itens)

        }

    }
}