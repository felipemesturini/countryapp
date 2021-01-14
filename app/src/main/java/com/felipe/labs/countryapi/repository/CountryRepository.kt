package com.felipe.labs.countryapi.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.felipe.labs.countryapi.data.DbCountry
import com.felipe.labs.countryapi.models.CURRENT_CACHE_ID
import com.felipe.labs.countryapi.models.Cache
import com.felipe.labs.countryapi.models.Country
import com.felipe.labs.countryapi.utils.DateOper
import com.felipe.labs.countryapi.web.asCountryList
import com.felipe.labs.countryapi.ws.CountryApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.await
import java.util.*


private const val TAG = "CountryRepository"

class CountryRepository(private val db: DbCountry) {
    val _items = MutableLiveData<List<Country>>()

    val countries: LiveData<List<Country>>
        get() = _items

    suspend fun fetchData() {
        withContext(Dispatchers.IO) {
            Log.i(TAG, "Find videos Data")
            var itens = db.countryDao.list()
            var cache = db.cacheDao.get()?.also {
                val current = Calendar.getInstance().time
                val last = it.lastTimeCheck
                if (DateOper.isInvalidMinutes(current, last)) {
                    it
                } else {
                    null
                }
            }
            if (itens.isEmpty() || cache == null) {
                val service = CountryApi.countryService()
                val response = service.listCall()
                val body = response.await()
                itens = body.asCountryList()
                db.countryDao.clear()
                db.countryDao.insert(itens)
                val cache = Cache(CURRENT_CACHE_ID, Calendar.getInstance().timeInMillis)
                db.cacheDao.insert(cache)
            }
            _items.postValue(itens)
        }
    }

    suspend fun clearData() = withContext(Dispatchers.IO) {
        db.countryDao.clear()
        db.cacheDao.clear()
        fetchData()
    }

//    fun String.countChars(block: (posicion: Int) -> Boolean): Int {
//        var count = 0
//        this.forEachIndexed { index, c ->
//            if (block(index)) count++
//        }
//        return count
//    }
//
//    fun String.countCharsUm(block: String.(post: Boolean) -> Unit): Int {
//        return 1
//    }
}