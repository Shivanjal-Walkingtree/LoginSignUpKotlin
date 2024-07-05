package com.example.login_registration_app_kt

import android.app.Dialog
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
        val cancelButton = dialog.findViewById<ImageView>(R.id.cancelButton)

        home.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Upload a Video is clicked", Toast.LENGTH_SHORT).show()
        }

        me.setOnClickListener {
            dialog.dismiss()
            Toast.makeText(this, "Create a short is clicked", Toast.LENGTH_SHORT).show()
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
