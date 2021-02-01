package com.example.newProject.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.newProject.LoginPage
import com.example.newProject.R
import com.example.newProject.databinding.ActivityCalendarBinding

class CalendarActivity : AppCompatActivity() {
    val TAG: String = "CalenderActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)
        Log.d(TAG, "CalendarActivity - onCreate() called")

        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "CalendarActivity - onSupportNavigateUp() called")
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            null
        )
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "CalendarActivity - onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "CalendarActivity - onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "CalendarActivity - onPause() called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "CalendarActivity - onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "CalendarActivity - onDestroy() called")
    }

    //로그아웃 선택시 로그인 페이지로 이동
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.Logout -> {
                startActivity(Intent(this, LoginPage::class.java))
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //메뉴 구현
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.default_menu, menu)
        return true
    }
}