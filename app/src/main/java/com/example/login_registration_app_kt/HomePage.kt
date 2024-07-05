package com.example.login_registration_app_kt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.login_registration_app_kt.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePage : AppCompatActivity() {

    private lateinit var binding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
    }
}
