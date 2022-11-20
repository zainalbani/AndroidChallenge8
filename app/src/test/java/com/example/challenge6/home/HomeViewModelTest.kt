package com.example.challenge6.home

import com.binartest.challenge8.model.movie.GetNowPlayingResponse
import com.binartest.challenge8.network.ApiServiceMovie
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class HomeViewModelTest {
    private lateinit var service: ApiServiceMovie

    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun testGetMovie(): Unit = runBlocking {
        val respMovie = mockk<Call<GetNowPlayingResponse>>()

        every{
            runBlocking {
                service.getNowPlaying()
            }
        } returns respMovie
        val result = service.getNowPlaying()
        runBlocking {
            service.getNowPlaying()
        }
        assertEquals(result, respMovie)

    }
}