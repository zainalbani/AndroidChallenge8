package com.example.challenge6.login

import com.example.challenge6.model.user.GetUserResponse
import com.example.challenge6.network.ApiServiceUser
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import retrofit2.Call

class LoginViewModelTest {
    lateinit var service : ApiServiceUser
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