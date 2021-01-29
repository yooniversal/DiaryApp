package com.example.newProject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.newProject.databinding.ActivitySignUpBinding
import com.example.newProject.db.Login
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    val TAG: String = "SignUp"

    interface SignUpService{
        @FormUrlEncoded
        @POST("/app_signup/")
        fun requestSignUp(
                //Input을 정의하는 곳
                @Field("username") username:String,
                @Field("password") password:String
        ) : Call<Login> //Output을 정의하는 곳
    }

    var login: Login? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_sign_up)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        val binding = ActivitySignUpBinding.inflate(layoutInflater)
        val intent = Intent(this, LoginPage::class.java)

        var retrofit = Retrofit.Builder()
            .baseUrl("http://172.30.1.32:8000")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        var signUpService: SignUpService = retrofit.create(SignUpService::class.java)

        binding.btnConfirm.setOnClickListener {
            var id = binding.EditID.text.toString()
            var pw = binding.EditPW.text.toString()
            var confirmpw = binding.EditConfimPW.text.toString()

            var dialog = AlertDialog.Builder(this@SignUpActivity)
            if (pw != confirmpw) {
                Log.d(TAG, "password is not same.")
                dialog.setTitle("알림")
                dialog.setMessage("비밀번호가 일치하지 않습니다.")
                dialog.show()
            }
            else {
                signUpService.requestSignUp(id, pw).enqueue(object : Callback<Login> {
                    override fun onFailure(call: Call<Login>, t: Throwable) {
                        //웹 통신에 실패했을 때
                        Log.e("SIGNUP", "${t.message}")
                        var dialog = AlertDialog.Builder(this@SignUpActivity)
                        dialog.setTitle("알림")
                        dialog.setMessage("통신에 실패했습니다.")
                        dialog.show()
                    }

                    override fun onResponse(call: Call<Login>, response: Response<Login>) {
                        //웹 통신에 성공했을 때. 응답값을 받아 옴.
                        var login = response.body() //code, msg
                        var dialog = AlertDialog.Builder(this@SignUpActivity)


                        //중복된 ID
                        if (login?.code == "1002") {
                            Log.d(TAG, "same ID is already exist.")
                            dialog.setTitle("알림")
                            dialog.setMessage(login?.msg)
                            dialog.show()
                        }
                        //회원가입 성공
                        else {
                            Log.d(TAG, "SignUp is successful!")
                            dialog.setTitle("알림")
                            dialog.setMessage(login?.msg)
                            dialog.show()

                            //startActivity(intent)
                            //finish()
                        }
                    }
                })
            }
        }

        binding.btnCancel.setOnClickListener {
            startActivity(intent)
            finish()
        }

        setContentView(binding.root)
    }
}