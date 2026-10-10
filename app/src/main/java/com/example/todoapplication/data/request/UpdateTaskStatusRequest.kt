package com.example.todoapplication.data.request

import com.example.todoapplication.data.response.TaskStatus

data class UpdateTaskStatusRequest(
    val status: TaskStatus
)
