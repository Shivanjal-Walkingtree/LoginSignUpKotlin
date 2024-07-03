package com.example.login_registration_app_kt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login : AppCompatActivity() {

    lateinit var email_login: EditText
    lateinit var password_login: EditText
    lateinit var button_login: Button
    lateinit var text_login: TextView
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        email_login = findViewById(R.id.email_login)
        password_login = findViewById(R.id.password_login)
        button_login = findViewById(R.id.Button_login)
        text_login = findViewById(R.id.text_login)
        progressBar = findViewById(R.id.progress_bar)


        val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", null)
        val password = sharedPreferences.getString("password", null)
        if (email != null && password != null && intent.getBooleanExtra("logout", false).not()) {
            progressBar.visibility = View.VISIBLE
            // Automatically log in with saved credentials
            signIn(email, password)
        }

        text_login.setOnClickListener {
            startActivity(Intent(this@Login, Registration::class.java))
            finish()
        }


        button_login.setOnClickListener {
            val email = email_login.text.toString()
            val password = password_login.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
                Toast.makeText(this@Login, "Both fields required !!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            signIn(email, password)
            progressBar.visibility = View.VISIBLE
        }
    }


    private fun signIn(email: String, password: String) {
        val request = SignInRequest().apply {
            this.email = email
            this.password = password
            this.isGoogleAuth = 0
        }

        RetrofitClient.instance.signIn(request).enqueue(object : Callback<SignInResponse?> {
            override fun onResponse(call: Call<SignInResponse?>, response: Response<SignInResponse?>) {
                if (response.isSuccessful) {
                    progressBar.visibility = View.GONE
                    val signInResponse = response.body()
                    // Log the response to see if fullName is included
                    Log.d("Login", "SignInResponse: $signInResponse")

                    val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("email", email)
                    editor.putString("password", password)
                    editor.apply()

                    val intent = Intent(this@Login, MainActivity::class.java)
                    intent.putExtra("signInResponse", signInResponse)
                    startActivity(intent)
                    finish()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.e("SignInError", "Error: ${response.code()}, Body: $errorBody")
                    Toast.makeText(this@Login, errorBody, Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SignInResponse?>, t: Throwable) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@Login, "Error : ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}

