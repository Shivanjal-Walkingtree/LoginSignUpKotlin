package com.example.login_registration_app_kt

data class SignUpRequest(
    var fullName: String = "",
    var email: String = "",
    var password: String = "",
    var role: String = "",
    var isGoogleAuth: Int = 0
)