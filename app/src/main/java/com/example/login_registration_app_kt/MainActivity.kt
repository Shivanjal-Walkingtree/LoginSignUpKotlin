package com.example.login_registration_app_kt

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {


    lateinit var name : TextView
    lateinit var fullname : TextView
    lateinit var email: TextView
    lateinit var userId : TextView
    lateinit var role : TextView
    lateinit var nmtx : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        name = findViewById(R.id.profileName)
        fullname = findViewById(R.id.fullname)
        email = findViewById(R.id.profileEmail)
        role = findViewById(R.id.role)
        userId = findViewById(R.id.userid)
        nmtx = findViewById(R.id.nametext)


        val intent = intent
        if (intent.extras != null) {
            val extras = intent.extras
            if (extras != null) {
                val signInResponse = extras.getSerializable("signInResponse") as SignInResponse?
                signInResponse?.let {
                    name.text = signInResponse.data?.fullName
                    fullname.text = signInResponse.data?.fullName
                    nmtx.text = signInResponse.data?.fullName
                    email.text = signInResponse.data?.email
                    role.text = signInResponse.data?.role
                    userId.text = signInResponse.data?.userId


                    Log.e("TAG", "====> ${it.data?.fullName}")
                }
            }
        }



//        val intent = Intent()
//        if (intent.extras!= null) {
//            val signInResponse = intent.getSerializableExtra("data") as SignInResponse
//            message.setText(signInResponse.message)
//            Log.e("TAG","====> "+signInResponse.message)
//
//        }
    }
}