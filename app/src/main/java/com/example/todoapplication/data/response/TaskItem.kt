package com.example.todoapplication.data.response

enum class TaskStatus {
    OPEN, IN_PROGRESS, DONE
}

data class TaskItem(
    val id: String,
    val title: String,
    val description: String,
    var status: TaskStatus
)