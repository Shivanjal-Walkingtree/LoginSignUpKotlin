package com.example.login_registration_app_kt

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("Login/")
    fun signIn(@Body request: SignInRequest): Call<SignInResponse>

    @POST("Register/")
    fun signUp(@Body request: SignUpRequest): Call<SignUpResponse>
}
