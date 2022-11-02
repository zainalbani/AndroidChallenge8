package com.example.challenge6.network

import com.example.challenge6.model.user.GetUserResponse
import com.example.challenge6.model.user.PostUserResponse
import com.example.challenge6.model.user.User
import com.example.challenge6.model.user.UserProfile
import retrofit2.Call
import retrofit2.http.*

interface ApiServiceUser {
    @GET("users")
    fun getAllUsers(): Call<List<GetUserResponse>>

    @POST("users")
    fun insertUser(@Body request: User): Call<PostUserResponse>

    @PUT("users/{id}")
    fun updateUser(@Path("id") id : Int, @Body request: UserProfile): Call<PostUserResponse>

    @GET("users/{id}")
    fun getUserById(@Path("id") id: Int): Call<GetUserResponse>

}