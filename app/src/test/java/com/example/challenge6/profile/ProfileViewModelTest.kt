package com.example.challenge6.profile

import com.binartest.challenge8.model.user.GetUserResponse
import com.binartest.challenge8.network.ApiServiceUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class ProfileViewModelTest {
    private lateinit var service : ApiServiceUser

    @Before
    fun setUp(){
        service = mockk()
    }

    @Test
    fun testGetUserById(){
        val id = 0
        val respGetUserById = mockk<Call<GetUserResponse>>()
        every{
            service.getUserById(id)
        } returns respGetUserById

        val result = service.getUserById(id)
            runBlocking {
                service.getUserById(id)
            }
            Assert.assertEquals(result, respGetUserById)
    }

}