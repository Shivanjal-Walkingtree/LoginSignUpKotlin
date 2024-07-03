package com.example.login_registration_app_kt

data class SignInRequest(
    var email: String = "",
    var password: String = "",
    var isGoogleAuth: Int = 0
)