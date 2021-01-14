package com.felipe.labs.countryapi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import com.felipe.labs.countryapi.viewmodel.ViewModelMain
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        findViewById<FloatingActionButton>(R.id.floatingActionButton).setOnClickListener(object: View.OnClickListener {
            override fun onClick(p0: View?) {
                mViewModelMain.refreshData()
            }

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