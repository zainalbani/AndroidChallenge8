package com.example.challenge6.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge6.model.user.GetUserResponse
import com.example.challenge6.network.ApiServiceUser
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor (private val client: ApiServiceUser): ViewModel(){
    private val _user: MutableLiveData<GetUserResponse?> = MutableLiveData()
    val user: LiveData<GetUserResponse?> get() = _user

    fun auth(email: String, password: String) {
        client.getAllUsers()
            .enqueue(object : Callback<List<GetUserResponse>> {
                override fun onResponse(
                    call: Call<List<GetUserResponse>>,
                    response: Response<List<GetUserResponse>>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            for (i in responseBody.indices) {
                                if (responseBody[i].email.equals(
                                        email.toString(),
                                        ignoreCase = false
                                    ) && responseBody[i].password.equals(
                                        password.toString(), ignoreCase = false
                                    )
                                ) {
                                    _user.postValue(responseBody[i])
                                } else {
                                    _user.postValue(null)
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<List<GetUserResponse>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
    }
}
