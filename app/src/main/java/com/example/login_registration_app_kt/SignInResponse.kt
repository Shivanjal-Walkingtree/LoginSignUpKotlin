package com.example.login_registration_app_kt

import java.io.Serializable


data class SignInResponse(
    var status: String = "",
    var statusCode: Int = 0,
    var message: String = "",
    var data: Data? = null
) : Serializable {
    data class Data(
        var userId: String = "",
        var fullName: String = "",
        var email: String = "",
        var role: String = ""
    ) : Serializable
}