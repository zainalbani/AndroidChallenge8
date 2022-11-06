package com.binartest.challenge8.model.user

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("username")
    val username: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("nama_lengkap")
    val namaLengkap: String?,
    @SerializedName("alamat")
    val alamat: String?,
    @SerializedName("status")
    val status: String?
)