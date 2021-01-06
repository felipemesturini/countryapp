package com.felipe.labs.countryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.felipe.labs.countryapi.web.CountryResponse
import com.felipe.labs.countryapi.ws.CountryApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val service = CountryApi.countryService()
        val response = service.listCall()

        response.enqueue(object : Callback<CountryResponse> {
            override fun onResponse(
                call: Call<CountryResponse>,
                response: Response<CountryResponse>
            ) {
                Log.i(TAG, "Message: ${response.message()} Status: ${response.code()}")
                findViewById<TextView>(R.id.tvMessage).text =  "Message: ${response.message()} Status: ${response.code()}"
                response.body()?.forEach {
                    Log.i(TAG, "Message: ${it.name}")
                }
            }

            override fun onFailure(call: Call<CountryResponse>, t: Throwable) {
                Log.i(TAG, "Message: ${t.message} Status: ${call.toString()}")
            }

        })
        CoroutineScope(Dispatchers.IO).launch {
            val defer = service.listResponse()
            val itens = defer
            Log.i(TAG, "Items: ${itens.size}")
            withContext(Dispatchers.Main) {
                findViewById<TextView>(R.id.tvMessage).text =  "Message: ${itens.first().name}"
            }

        }

    }
}