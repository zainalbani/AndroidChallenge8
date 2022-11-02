package com.example.challenge6.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.challenge6.model.movie.MovieDetailData
import com.example.challenge6.network.ApiServiceMovie
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val client: ApiServiceMovie
):ViewModel() {

    private val _movie: MutableLiveData<MovieDetailData?> = MutableLiveData()
    val movie: LiveData<MovieDetailData?> get() = _movie
    
    fun getMovieById(id: Int) {
        client.getDetailMovie(id)
            .enqueue(object : Callback<MovieDetailData> {
                override fun onResponse(
                    call: Call<MovieDetailData>,
                    response: Response<MovieDetailData>
                ) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody != null) {
                            _movie.postValue(responseBody)
                        }
                    }
                }

                override fun onFailure(call: Call<MovieDetailData>, t: Throwable) {

                }

            })
    }
}

