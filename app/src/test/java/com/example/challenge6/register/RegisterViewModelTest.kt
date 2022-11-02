package com.example.challenge6.register

import com.example.challenge6.model.user.GetUserResponse
import com.example.challenge6.model.user.PostUserResponse
import com.example.challenge6.model.user.User
import com.example.challenge6.network.ApiServiceMovie
import com.example.challenge6.network.ApiServiceUser
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import retrofit2.Call

class RegisterViewModelTest {
lateinit var service : ApiServiceUser
    @Before
    fun setUp() {
        service = mockk()
    }

    @Test
    fun insertUser(){
        val respInsertUser = mockk<Call<PostUserResponse>>()
        every{
            service.insertUser(User("","","","","",""))
        } returns respInsertUser

        val result = service.insertUser(User("","","","","",""))
        runBlocking {
            service.insertUser(User("","","","","",""))
        }
        Assert.assertEquals(result, respInsertUser)
    }
}