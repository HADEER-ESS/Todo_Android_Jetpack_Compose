package com.example.todoapplication.Api

import com.example.todoapplication.data.request.CreateTask
import com.example.todoapplication.data.request.CreateUser
import com.example.todoapplication.data.response.LoginResponse
import com.example.todoapplication.data.response.TaskItem
import com.example.todoapplication.data.response.TaskStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("tasks")
    suspend fun getTasks() : Response<List<TaskItem>>

    @POST("tasks")
    suspend fun createTask(@Body() body: CreateTask): Response<TaskItem>

    @PATCH("tasks/{id}/status")
    suspend fun updateTaskStatus(
        @Path("id") id: String,
        @Body() status: TaskStatus
    ): Response<TaskItem>

    @GET("tasks/{id}")
    suspend fun getTaskById(
        @Path("id") id: String
    ): Response<TaskItem>

    @DELETE("tasks/{id}")
    suspend fun deleteTask(
        @Path("id") id: String
    ): Response<Void>

    @POST("auth/signup")
    suspend fun createAccount(
        @Body() body: CreateUser
    ): Response<Void>

    @POST("auth/signin")
    suspend fun login(@Body() body: CreateUser): Response<LoginResponse>
}