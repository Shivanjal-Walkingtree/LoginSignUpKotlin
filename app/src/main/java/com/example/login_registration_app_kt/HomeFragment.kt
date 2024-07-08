package com.example.login_registration_app_kt

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit

class HomeFragment : Fragment() {

    private lateinit var name: TextView
    private lateinit var buttonLogout: ImageView
    private lateinit var me_button: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        name = view.findViewById(R.id.user_name)
        buttonLogout = view.findViewById(R.id.button_logout)
        me_button = view.findViewById(R.id.me_button)

        val intent = activity?.intent
        intent?.extras?.let { extras ->
            val signInResponse = extras.getSerializable("signInResponse") as SignInResponse?
            signInResponse?.let {
                name.text = it.data?.fullName ?: "N/A"
            }
        }

        buttonLogout.setOnClickListener {
            // Clear SharedPreferences
            val sharedPreferences = activity?.getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            val editor = sharedPreferences?.edit()
            editor?.clear()
            editor?.apply()

            // Redirect to login screen
            val loginIntent = Intent(activity, Login::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            loginIntent.putExtra("logout", true)
            startActivity(loginIntent)
            activity?.finish()
        }

        me_button.setOnClickListener {
            navigateToMe()
        }
        return view
    }

    private fun navigateToMe() {

        parentFragmentManager.commit {
            replace(R.id.frame_layout, MeFragment())
            addToBackStack(null) // Optional: adds the transaction to the back stack
        }

    }
}
