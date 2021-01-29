package com.example.newProject

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.newProject.databinding.ActivityLoginPageBinding
import com.example.newProject.db.Login
import com.example.newProject.ui.CalendarActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    val TAG: String = "LOGIN"

    interface LoginService{
        @FormUrlEncoded
        @POST("/app_login/")
        fun requestLogin(
                //Input을 정의하는 곳
                @Field("username") userid:String,
                @Field("password") userpw:String
        ) : Call<Login> //Output을 정의하는 곳
    }

    var login: Login? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        Log.d(TAG, "LoginPage - onCreate() called")
        val binding = ActivityLoginPageBinding.inflate(layoutInflater)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.32:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)
        val current = this

        binding.btnLogin.setOnClickListener(){
            var id = binding.EditID.text.toString()
            var pw = binding.EditPW.text.toString()

            loginService.requestLogin(id, pw).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    //웹 통신에 실패했을 때
                    Log.e(TAG, "${t.message}")
                    var dialog = AlertDialog.Builder(this@LoginPage)
                    dialog.setTitle("알림")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    //웹 통신에 성공했을 때. 응답값을 받아 옴.
                    Log.d(TAG, "통신 성공")
                    var login = response.body() //code, msg
                    var dialog = AlertDialog.Builder(this@LoginPage)
                    dialog.setTitle("알림")
                    dialog.setMessage(login?.msg)
                    dialog.show()

                    if(login?.code == "0000"){
                        startActivity(Intent(current, CalendarActivity::class.java))
                        finish()
                    }
                }
            })
        }

        binding.btnSignIn.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }

        setContentView(binding.root)
    }
}