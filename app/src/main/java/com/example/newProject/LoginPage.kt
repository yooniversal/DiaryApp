package com.example.newProject

import android.R
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toolbar
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


/*
//firebase Auth
private lateinit var firebaseAuth: FirebaseAuth
//google client
private lateinit var googleSignInClient: GoogleSignInClient

//private const val TAG = "GoogleActivity"
private val RC_SIGN_IN = 99
*/

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
//        setContentView(R.layout.activity_login_page)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)
        Log.d(TAG, "LoginPage - onCreate() called")
        val binding = ActivityLoginPageBinding.inflate(layoutInflater)

        /*
        //Google 로그인 버튼
        binding.btnGoogleSignIn.setOnClickListener(){
            Google_login()
        }

        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()
         */

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

    /*
    // onStart. 유저가 앱에 이미 구글 로그인을 했는지 확인
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account !== null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    }

    // onActivityResult
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    } // onActivityResult End

    // firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Log.w("LoginActivity", "firebaseAuthWithGoogle 성공", task.exception)
                        toMainActivity(firebaseAuth?.currentUser)
                    } else {
                        Log.w("LoginActivity", "firebaseAuthWithGoogle 실패", task.exception)
                        Toast.makeText(this, "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
    }// firebaseAuthWithGoogle END

    // toMainActivity
    fun toMainActivity(user: FirebaseUser?) {
        if(user !=null) { // MainActivity 로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    // signIn
    private fun Google_login() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() { // 로그아웃
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }

    private fun revokeAccess() { //회원탈퇴
        // Firebase sign out
        firebaseAuth.signOut()
        googleSignInClient.revokeAccess().addOnCompleteListener(this) {

        }
    }
     */
}