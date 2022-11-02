package com.example.challenge6.detail

import com.example.challenge6.model.movie.MovieDetailData
import com.example.challenge6.network.ApiServiceMovie
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class DetailViewModelTest {
    lateinit var service: ApiServiceMovie

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun testGetMovieById() {
        val id = 0
        val respGetMovieById = mockk<Call<MovieDetailData>>()
        every {
            service.getDetailMovie(id)
        } returns respGetMovieById

        val result = service.getDetailMovie(id)
        runBlocking {
            service.getDetailMovie(id)
        }
        Assert.assertEquals(result, respGetMovieById)
    }
}
