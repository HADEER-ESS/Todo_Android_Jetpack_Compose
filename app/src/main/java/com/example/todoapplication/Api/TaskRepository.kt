package com.example.todoapplication.Api

import com.example.todoapplication.data.request.CreateTask
import com.example.todoapplication.data.response.TaskItem
import com.example.todoapplication.data.response.TaskStatus
import retrofit2.Response

class TaskRepository {
    private val api = AppModule.api

    suspend fun getTasks(token: String): Response<List<TaskItem>>{
        return api.getTasks("Bearer $token")
    }

    suspend fun getTaskById(token: String, id: String): Response<TaskItem>{
        return api.getTaskById("Bearer $token",id)
    }

    suspend fun createTask(token: String,body: CreateTask): Response<TaskItem> {
        return api.createTask("Bearer $token",  body)
    }

    suspend fun updateTaskStatus(token: String,id: String, status: TaskStatus): Response<TaskItem>{
        return api.updateTaskStatus("Bearer $token",id, status)
    }

    suspend fun deleteTask(token: String,id: String): Response<Void>{
        return api.deleteTask("Bearer $token",id)
    }
}