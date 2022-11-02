package com.example.challenge6.network

import com.example.challenge6.model.user.GetUserResponse
import com.example.challenge6.model.user.PostUserResponse
import com.example.challenge6.model.user.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiServiceUser {
    @GET("users")
    fun getAllUsers(): Call<List<GetUserResponse>>

    @POST("users")
    fun insertUser(@Body request: User): Call<PostUserResponse>
}