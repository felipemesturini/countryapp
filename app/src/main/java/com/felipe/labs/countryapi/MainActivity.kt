package com.felipe.labs.countryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.felipe.labs.countryapi.viewmodel.ViewModelMain
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
    private lateinit var mRecyclerView: RecyclerView
    private lateinit var mViewModelMain: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupRecyclerView()
        mViewModelMain = ViewModelProvider(this).get(ViewModelMain::class.java)
        mViewModelMain.items.observe(this, Observer {
            Log.i(TAG, it.toString())
            (mRecyclerView.adapter as CountryAdapter).submitLit(it)
        })


    }

    private fun setupRecyclerView() {
        mRecyclerView = findViewById(R.id.countryRecyclerView)
        mRecyclerView.apply{
            adapter = CountryAdapter() { position ->
                Log.i(TAG, "Position: $position")
            }
            setHasFixedSize(true)
            addItemDecoration(DividerItemDecoration(baseContext, DividerItemDecoration.VERTICAL))
        }
    }
}