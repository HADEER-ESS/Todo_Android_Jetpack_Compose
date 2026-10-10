package com.example.todoapplication.Api

import com.example.todoapplication.data.request.CreateUser
import com.example.todoapplication.data.response.LoginResponse
import retrofit2.Response

class UserRepository {
    private val api = AppModule.api

    suspend fun createAccount(body : CreateUser): Response<Void>{
        return api.createAccount(body)
    }

    suspend fun login(body: CreateUser): Response<LoginResponse>{
        return api.login(body)
    }
}