package com.example.login_registration_app_kt

import java.io.Serializable

data class SignUpResponse(
    var status: String = "",
    var statusCode: Int? = null,
    var message: String = "",
    var data: Data? = null
) : Serializable {
    data class Data(
        var userId: String = "",
        var fullName: String = "",
    ):Serializable
}









