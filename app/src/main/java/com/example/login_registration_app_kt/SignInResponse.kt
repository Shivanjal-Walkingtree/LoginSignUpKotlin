package com.example.login_registration_app_kt

import java.io.Serializable


class SignInResponse : Serializable {
    var status: String = ""
    var statusCode: Int = 0
    var message: String = ""
    var data: Data? = null

    class Data : Serializable {
        var userId: String = ""
        var fullName: String = ""
        var email: String = ""
        var role: String = ""
    }
}
