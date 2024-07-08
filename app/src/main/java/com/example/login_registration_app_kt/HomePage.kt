package com.example.login_registration_app_kt

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.login_registration_app_kt.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlin.math.log

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding
    private lateinit var fab : FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fab = findViewById(R.id.fab)

        // Set initial fragment
        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                replace(R.id.frame_layout, HomeFragment())
            }
        }

        // Handle bottom navigation view item selection
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    supportFragmentManager.commit {
                        replace(R.id.frame_layout, HomeFragment())
                    }
                    true
                }
                R.id.navigation_me -> {
                    supportFragmentManager.commit {
                        replace(R.id.frame_layout, MeFragment())
                    }
                    true
                }
                else -> false
            }
        }

        binding.fab.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                showBottomDialog()
            }
        })

    }



    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottomsheet_layout)

        val home = dialog.findViewById<LinearLayout>(R.id.home)
        val me = dialog.findViewById<LinearLayout>(R.id.me)
        val logout = dialog.findViewById<LinearLayout>(R.id.logout)
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)

        home.setOnClickListener {
            dialog.dismiss()
            supportFragmentManager.commit {
                replace(R.id.frame_layout, HomeFragment())
            }
        }

        me.setOnClickListener {
            dialog.dismiss()
            supportFragmentManager.commit {
                replace(R.id.frame_layout, MeFragment())
            }
        }

        logout.setOnClickListener {
            dialog.dismiss()
            val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.clear()
            editor.apply()
            startActivity(Intent(this@HomePage, Login::class.java))
            finish()
        }

        cancelButton.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.window?.setGravity(Gravity.BOTTOM)
    }
}
