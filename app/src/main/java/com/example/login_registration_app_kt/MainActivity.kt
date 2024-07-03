package com.example.login_registration_app_kt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var name: TextView
    lateinit var fullname: TextView
    lateinit var email: TextView
    lateinit var userId: TextView
    lateinit var role: TextView
    lateinit var nmtx: TextView
    lateinit var buttonLogout: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.profileName)
        fullname = findViewById(R.id.fullname)
        email = findViewById(R.id.profileEmail)
        role = findViewById(R.id.role)
        userId = findViewById(R.id.userid)
        nmtx = findViewById(R.id.nametext)

        buttonLogout = findViewById(R.id.button_logout)


        val intent = intent
        if (intent.extras != null) {
            val extras = intent.extras
            if (extras != null) {
                val signInResponse = extras.getSerializable("signInResponse") as SignInResponse?
                signInResponse?.let {
                    // Log the data being received

                    name.text = it.data?.fullName ?: "N/A"
                    fullname.text = it.data?.fullName ?: "N/A"
                    nmtx.text = it.data?.fullName ?: "N/A"
                    email.text = it.data?.email ?: "N/A"
                    role.text = it.data?.role ?: "N/A"
                    userId.text = it.data?.userId ?: "N/A"
                }
            }
        }

        buttonLogout.setOnClickListener {

            // Clear SharedPreferences
            val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()

            // Redirect to login screen
            val loginIntent = Intent(this@MainActivity, Login::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            loginIntent.putExtra("logout", true)
            startActivity(loginIntent)
            finish()
        }
    }
}
