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

class MeFragment : Fragment() {

    private lateinit var name: TextView
    private lateinit var fullname: TextView
    private lateinit var email: TextView
    private lateinit var userId: TextView
    private lateinit var role: TextView
    private lateinit var nmtx: TextView
    private lateinit var buttonLogout: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_me, container, false)

        name = view.findViewById(R.id.profileName)
        fullname = view.findViewById(R.id.fullname)
        email = view.findViewById(R.id.profileEmail)
        role = view.findViewById(R.id.role)
        userId = view.findViewById(R.id.userid)
        nmtx = view.findViewById(R.id.nametext)
        buttonLogout = view.findViewById(R.id.button_logout)

        val intent = activity?.intent
        intent?.extras?.let { extras ->
            val signInResponse = extras.getSerializable("signInResponse") as SignInResponse?
            signInResponse?.let {
                name.text = it.data?.fullName ?: "N/A"
                fullname.text = it.data?.fullName ?: "N/A"
                nmtx.text = it.data?.fullName ?: "N/A"
                email.text = it.data?.email ?: "N/A"
                role.text = it.data?.role ?: "N/A"
                userId.text = it.data?.userId ?: "N/A"
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

        return view
    }
}
