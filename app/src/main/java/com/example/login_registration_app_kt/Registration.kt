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

class Registration : AppCompatActivity() {

    lateinit var username_signup: EditText
    lateinit var email_sign:EditText
    lateinit var password_signup:EditText
    lateinit var role_signup:EditText
    lateinit var button_signup:Button
    lateinit var text_signup:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        username_signup = findViewById(R.id.username_signup)
        email_sign = findViewById(R.id.email_signup)
        role_signup = findViewById(R.id.role_signup)
        password_signup = findViewById(R.id.password_signup)
        button_signup = findViewById(R.id.button_signup)
        text_signup = findViewById(R.id.text_signup)

        button_signup.setOnClickListener(object : View.OnClickListener {

            override fun onClick(p0: View?) {
                if(TextUtils.isEmpty(email_sign.text.toString()) || TextUtils.isEmpty(password_signup.text.toString()) ||  TextUtils.isEmpty(username_signup.text.toString())
                    || TextUtils.isEmpty(role_signup.text.toString()) ){
                    Toast.makeText(this@Registration, "All fields required !!", Toast.LENGTH_SHORT).show();
                }
                var request = SignUpRequest()
                request.fullName = username_signup.text.toString()
                request.email = email_sign.text.toString()
                request.password = password_signup.text.toString()
                request.role = role_signup.text.toString()
                request.isGoogleAuth = 0



                RetrofitClient.instance.signUp(request).enqueue(object : Callback<SignUpResponse?> {
                    override fun onResponse(
                        call: Call<SignUpResponse?>,
                        response: Response<SignUpResponse?>
                    ) {
                        if (response.isSuccessful) {

                                val signUpResponse = response.body()
                                val intent = Intent(this@Registration, Login::class.java)
                                intent.putExtra("signUpResponse", signUpResponse)
                                startActivity(intent)
                                finish()

                        } else {
                            val errorBody = response.errorBody()?.string()
                            Log.e("SignUpError", "Error: ${response.code()}, Body: $errorBody")
                            Toast.makeText(this@Registration, "Sign-Up Failed", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<SignUpResponse?>, t: Throwable) {
                        Toast.makeText(this@Registration, "Error: ${t.message}", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
        })

        text_signup.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                startActivity(Intent(this@Registration, Login::class.java))
            }
        })



    }
}