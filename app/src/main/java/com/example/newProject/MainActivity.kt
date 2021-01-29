package com.example.newProject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.content.Intent;
import com.example.newProject.databinding.ActivityMainBinding
import com.example.newProject.ui.CalendarActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    val TAG: String = "Splash Screen 로그"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // R(res)/layout/activity_main.xml 연결
        setContentView(R.layout.activity_main)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        /*
        Timer().schedule(2000){
            //JUST DELAY 2 SEC
        }
        */
        
        Log.d(TAG, "MainActivity - onCreate() called")
    }

    override fun onStart() {
        super.onStart()
        val binding = ActivityMainBinding.inflate(layoutInflater)

        binding.textView.visibility = View.INVISIBLE

        setContentView(binding.root)
        Log.d(TAG, "MainActivity - onStart() called")
    }

    override fun onResume() {
        super.onResume()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textView.visibility = View.INVISIBLE
        setContentView(binding.root)
        Log.d(TAG, "MainActivity - onResume() called")
    }

    override fun onPause() {
        super.onPause()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        binding.textView.visibility = View.VISIBLE
        setContentView(binding.root)
        Log.d(TAG, "MainActivity - onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "MainActivity - onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "MainActivity - onDestroy() called")
    }
}