package com.example.todoapplication.Api

import com.example.todoapplication.data.request.CreateTask
import com.example.todoapplication.data.response.TaskItem
import com.example.todoapplication.data.response.TaskStatus
import retrofit2.Response

class TaskRepository {
    private val api = AppModule.api

    suspend fun getTasks(): Response<List<TaskItem>>{
        return api.getTasks()
    }

    suspend fun getTaskById(id: String): Response<TaskItem>{
        return api.getTaskById(id)
    }

    suspend fun createTask(body: CreateTask): Response<TaskItem> {
        return api.createTask(body)
    }

    suspend fun updateTaskStatus(id: String, status: TaskStatus): Response<TaskItem>{
        return api.updateTaskStatus(id, status)
    }

    suspend fun deleteTask(id: String): Response<Void>{
        return api.deleteTask(id)
    }
}