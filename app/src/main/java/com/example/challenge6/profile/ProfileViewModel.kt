package com.example.challenge6.profile

import android.app.Application
import android.net.Uri
import androidx.lifecycle.*
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.challenge6.datastore.UserManager
import com.example.challenge6.model.user.GetUserResponse
import com.example.challenge6.model.user.PostUserResponse
import com.example.challenge6.model.user.UserProfile
import com.example.challenge6.network.ApiServiceUser
import com.example.challenge6.worker.BlurWorker
import com.example.challenge6.worker.KEY_IMAGE_URI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val pref: UserManager,
    private val client: ApiServiceUser,
    application: Application
) : ViewModel() {
    private val _user: MutableLiveData<GetUserResponse?> = MutableLiveData()
    val user: LiveData<GetUserResponse?> get() = _user

    private val workManager = WorkManager.getInstance(application)

    private var imageUri: Uri? = null

    fun setImageUri(uri: Uri?) {
        imageUri = uri
    }

    fun getUserById(id: Int) {
        client.getUserById(id)
            .enqueue(object : Callback<GetUserResponse> {
                override fun onResponse(
                    call: Call<GetUserResponse>,
                    response: Response<GetUserResponse>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _user.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<GetUserResponse>, t: Throwable) {
                }
            })
    }

    fun updateUser(
        id: Int,
        username: String,
        namaLengkap: String,
        alamat: String,
        status: String
    ) {
        client.updateUser(id, UserProfile(username, namaLengkap, alamat, status))
            .enqueue(object : Callback<PostUserResponse> {
                override fun onResponse(
                    call: Call<PostUserResponse>,
                    response: Response<PostUserResponse>
                ) {
                }

                override fun onFailure(call: Call<PostUserResponse>, t: Throwable) {
                }
            })
    }
    private fun createInputDataForUri(): Data {
        val builder = Data.Builder()
        imageUri?.let {
            builder.putString(KEY_IMAGE_URI, imageUri.toString())
        }
        return builder.build()
    }

        fun applyBlur(){
        val blurRequest = OneTimeWorkRequestBuilder<BlurWorker>()
            .setInputData(createInputDataForUri())
            .build()
        workManager.enqueue(blurRequest)
    }
    fun removeImage() {
        viewModelScope.launch {
            pref.removeImage()
        }
    }


    fun removeIsLoginStatus() {
        viewModelScope.launch {
            pref.removeIsLoginStatus()
        }
    }

    fun removeUsername() {
        viewModelScope.launch {
            pref.removeUsername()
        }
    }

    fun removeId() {
        viewModelScope.launch {
            pref.removeId()
        }
    }

    fun getDataStoreIsLogin(): LiveData<Boolean> {
        return pref.getIsLogin.asLiveData()
    }

    fun getId(): LiveData<Int> {
        return pref.getId.asLiveData()
    }

    fun saveUsername(username: String) {
        viewModelScope.launch {
            pref.saveUsername(username)
        }
    }

    fun saveImage(uri: String) {
        viewModelScope.launch {
            pref.saveProfileImage(uri)
        }
    }


}