package com.binartest.challenge8.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.binartest.challenge8.model.user.PostUserResponse
import com.binartest.challenge8.model.user.User
import com.binartest.challenge8.network.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val client: ApiServiceUser) : ViewModel() {
    private val _user: MutableLiveData<PostUserResponse?> = MutableLiveData()

    fun insertUser(username: String, email: String, password: String) {
        client.insertUser(
            User(username, email, password,"","","")
        )
            .enqueue(object : retrofit2.Callback<PostUserResponse> {
                override fun onResponse(
                    call: retrofit2.Call<PostUserResponse>,
                    response: retrofit2.Response<PostUserResponse>
                ) {
                    if (response.isSuccessful) {
                        _user.postValue(response.body())
                    } else {
                        _user.postValue(null)
                    }
                }

                override fun onFailure(call: retrofit2.Call<PostUserResponse>, t: Throwable) {
                    _user.postValue(null)
                }

            })
    }
}