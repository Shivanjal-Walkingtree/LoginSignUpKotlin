package com.example.login_registration_app_kt

import java.io.Serializable

class SignUpResponse : Serializable{
    var status: String = ""
    var statusCode: Int? = null
    var message: String = ""
    var data: Data? = null

    class Data : Serializable{
        var userId: String = ""
        var fullName: String = ""
        var email: String = ""
        var role: String = ""
    }
}

