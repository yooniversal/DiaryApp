package com.example.newProject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.example.newProject.databinding.ActivityLoginPageBinding

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        val binding = ActivityLoginPageBinding.inflate(layoutInflater)
        binding.btnLogin.setOnClickListener(){ //로그인 버튼 눌렀을 때 다음 상태 만들기
            var id = binding.EditID.toString()
            var pw = binding.EditPW.toString()

            var dialog = AlertDialog.Builder(this@LoginPage)
            dialog.setTitle("알람")
            dialog.setMessage("id : " + id)
            dialog.show()
        }
        setContentView(binding.root)
    }

}