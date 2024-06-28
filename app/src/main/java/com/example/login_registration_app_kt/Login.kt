package com.example.login_registration_app_kt

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Login : AppCompatActivity() {

    lateinit var email_login : EditText
    lateinit var password_login : EditText
    lateinit var button_login : Button
    lateinit var text_login : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        email_login = findViewById(R.id.email_login);
        password_login = findViewById(R.id.password_login);
        button_login = findViewById(R.id.Button_login)
        text_login = findViewById(R.id.text_login)



        button_login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

                if (TextUtils.isEmpty(email_login.text.toString()) || TextUtils.isEmpty(
                        password_login.text.toString())) {
                    Toast.makeText(this@Login, "Both fields required !!", Toast.LENGTH_SHORT)
                        .show();
                }
                val request = SignInRequest()
                request.email = email_login.text.toString()
                request.password = password_login.text.toString()
                request.isGoogleAuth = 0

                RetrofitClient.instance.signIn(request)
                    .enqueue(object : Callback<SignInResponse?> {
                        override fun onResponse(
                            call: Call<SignInResponse?>,
                            response: Response<SignInResponse?>
                        ) {
                            if (response.isSuccessful) {
                                var signInResponse = response.body()
                                val intent = Intent(this@Login, MainActivity::class.java)
                                intent.putExtra("signInResponse", signInResponse)
                                startActivity(intent)
                                finish()
                            } else {
                                val errorBody = response.errorBody()?.string()
                                Log.e("SignInError","error: ${response.code()},Body:$errorBody")
                                Toast.makeText(
                                    this@Login,
                                    "SignIn Failed !!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {

                            Log.e("SignInFailure","Error: ${t.message}")
                            Toast.makeText(
                                this@Login,
                                "Error : ${t.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        })

        text_login.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@Login, Registration::class.java))
                finish();
            }
        })
    }
}