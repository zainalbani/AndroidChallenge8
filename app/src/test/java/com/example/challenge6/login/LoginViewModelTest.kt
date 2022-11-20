package com.example.challenge6.login

import com.binartest.challenge8.model.user.GetUserResponse
import com.binartest.challenge8.network.ApiServiceUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class LoginViewModelTest {
    private lateinit var service : ApiServiceUser
    @Before
    fun setUp() {
        service = mockk ()
    }

    @Test
    fun getUser(): Unit = runBlocking {
        val respAllUser = mockk<Call<List<GetUserResponse>>>()

        every{
            runBlocking {
                service.getAllUsers()
            }
        } returns respAllUser

        val result = service.getAllUsers()

            runBlocking {
                service.getAllUsers()
            }
            assertEquals(result, respAllUser)
    }

}