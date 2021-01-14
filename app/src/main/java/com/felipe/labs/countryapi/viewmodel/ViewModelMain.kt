package com.felipe.labs.countryapi.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.felipe.labs.countryapi.data.DbCountry
import com.felipe.labs.countryapi.models.Country
import com.felipe.labs.countryapi.repository.CountryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelMain(app: Application): AndroidViewModel(app) {
    private val _repository = CountryRepository(DbCountry(getApplication()))

    val items: LiveData<List<Country>>
        get() = this._repository.countries

    init {
//        val db = DbCountry.getInstance(getApplication(), viewModelScope)
//        mRepository = CountryRepository(db)
        fetchData()
    }

    private fun fetchData() {
        viewModelScope.launch {
            _repository.fetchData()
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _repository.clearData()
        }
    }
}