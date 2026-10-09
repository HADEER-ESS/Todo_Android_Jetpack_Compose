package com.example.todoapplication.Api

import com.example.todoapplication.data.request.CreateUser
import retrofit2.Response

class UserRepository {
    private val api = AppModule.api

    suspend fun createAccount(body : CreateUser){
        api.createAccount(body)
    }

    suspend fun login(body: CreateUser): Response<String>{
        return api.login(body)
    }
}