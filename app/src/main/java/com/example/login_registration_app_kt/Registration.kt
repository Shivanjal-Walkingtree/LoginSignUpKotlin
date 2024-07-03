package com.example.login_registration_app_kt

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils

import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Registration : AppCompatActivity() {

    lateinit var username_sign: EditText
    lateinit var email_sign: EditText
    lateinit var password_signup: EditText
    lateinit var role_signup: EditText
    lateinit var button_signup: Button
    lateinit var text_signup: TextView
    lateinit var progressBar: ProgressBar


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        username_sign = findViewById(R.id.username_signup)
        email_sign = findViewById(R.id.email_signup)
        role_signup = findViewById(R.id.role_signup)
        password_signup = findViewById(R.id.password_signup)
        button_signup = findViewById(R.id.button_signup)
        text_signup = findViewById(R.id.text_signup)
        progressBar = findViewById(R.id.progress_bar)



        button_signup.setOnClickListener {
            if (TextUtils.isEmpty(email_sign.text.toString()) || TextUtils.isEmpty(password_signup.text.toString()) || TextUtils.isEmpty(username_sign.text.toString()) || TextUtils.isEmpty(role_signup.text.toString())) {
                Toast.makeText(this@Registration, "All fields required !!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val request = SignUpRequest().apply {
                progressBar.visibility = View.VISIBLE
                fullName = username_sign.text.toString()
                email = email_sign.text.toString()
                password = password_signup.text.toString()
                role = role_signup.text.toString()
                isGoogleAuth = 0
            }

            RetrofitClient.instance.signUp(request).enqueue(object : Callback<SignUpResponse?> {
                override fun onResponse(call: Call<SignUpResponse?>, response: Response<SignUpResponse?>) {
                    if (response.isSuccessful) {
                        progressBar.visibility = View.GONE
                        val signUpResponse = response.body()

                        // Log the response to see if fullName is included
                        Log.d("Registration", "SignUpResponse: $signUpResponse")

                        // Save email and password to SharedPreferences
                        val sharedPreferences: SharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE)
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("email", request.email)
                        editor.putString("password", request.password)
                        editor.apply()

                        val intent = Intent(this@Registration, Login::class.java)
                        intent.putExtra("signUpResponse", signUpResponse)
                        startActivity(intent)
                        finish()
                    } else {
                        val errorBody = response.errorBody()?.string()
                        Log.e("SignUpError", "Error: ${response.code()}, Body: $errorBody")
                        Toast.makeText(this@Registration, errorBody, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<SignUpResponse?>, t: Throwable) {
                    progressBar.visibility = View.GONE
                    Toast.makeText(this@Registration, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

        text_signup.setOnClickListener {
            startActivity(Intent(this@Registration, Login::class.java))
        }
    }

//    private fun isPasswordValid(password: String): Boolean {
//        // Define basic password strength criteria
//        val minLength = 6
//        val hasUppercase = password.any { it.isUpperCase() }
//        val hasLowercase = password.any { it.isLowerCase() }
//        val hasDigit = password.any { it.isDigit() }
//
//        return password.length >= minLength &&
//                hasUppercase &&
//                hasLowercase &&
//                hasDigit &&
//                !password.contains("\\s+".toRegex()) // No whitespace allowed
//    }

}

