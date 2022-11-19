package com.binartest.challenge8.home

import androidx.lifecycle.*
import com.binartest.challenge8.datastore.UserManager
import com.binartest.challenge8.model.movie.GetNowPlayingResponse
import com.binartest.challenge8.model.movie.GetNowPlayingResponseItem
import com.binartest.challenge8.network.ApiServiceMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val movieClient: ApiServiceMovie,
    private val pref: UserManager
) : ViewModel() {
    private val _movie: MutableLiveData<List<GetNowPlayingResponseItem>> = MutableLiveData()
    val movie: LiveData<List<GetNowPlayingResponseItem>> get() = _movie

    fun setMoviesList() {
        movieClient.getNowPlaying()
            .enqueue(object : Callback<GetNowPlayingResponse> {
                override fun onResponse(
                    call: Call<GetNowPlayingResponse>,
                    response: Response<GetNowPlayingResponse>
                ) {
                    if (response.isSuccessful) {
                        val data = response.body()
                        if (data != null) {
                            _movie.postValue(data.results as List<GetNowPlayingResponseItem>?)
                        }
                    }
                }

                override fun onFailure(call: Call<GetNowPlayingResponse>, t: Throwable) {

                }
            })
    }
    fun getDataStoreUsername(): LiveData<String>{
        return pref.getUsername.asLiveData()
    }
}