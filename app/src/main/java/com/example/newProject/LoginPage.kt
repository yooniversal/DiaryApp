package com.example.newProject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.newProject.databinding.ActivityLoginPageBinding
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

    interface LoginService{

        @FormUrlEncoded
        @POST("/app_login/")
        fun requestLogin(
                //Input을 정의하는 곳
                @Field("userid") userid:String,
                @Field("userpw") userpw:String
        ) : Call<Login> //Output을 정의하는 곳
    }

    var login:Login? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)
        val binding = ActivityLoginPageBinding.inflate(layoutInflater)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.32:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var loginService: LoginService = retrofit.create(LoginService::class.java)

        binding.btnLogin.setOnClickListener(){ //로그인 버튼 눌렀을 때 다음 상태 만들기
            var textId = binding.EditID.text.toString()
            var textPw = binding.EditPW.text.toString()

            loginService.requestLogin(textId, textPw).enqueue(object: Callback<Login> {
                override fun onFailure(call: Call<Login>, t: Throwable) {
                    //웹 통신에 실패했을 때
                    Log.e("LOGIN", "${t.message}")
                    var dialog = AlertDialog.Builder(this@LoginPage)
                    dialog.setTitle("알림")
                    dialog.setMessage("통신에 실패했습니다.")
                    dialog.show()
                }

                override fun onResponse(call: Call<Login>, response: Response<Login>) {
                    //웹 통신에 성공했을 때. 응답값을 받아 옴.
                    var login = response.body() //code, msg
                    var dialog = AlertDialog.Builder(this@LoginPage)
                    dialog.setTitle("알림")
                    dialog.setMessage("code : " + login?.code + " msg : " + login?.msg)
                    dialog.show()
                }

            })

        }

        setContentView(binding.root)
    }
}