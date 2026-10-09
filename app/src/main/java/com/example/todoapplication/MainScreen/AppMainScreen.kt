package com.example.todoapplication.MainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.Component.MainAppTitle
import com.example.todoapplication.Component.TaskItem
import com.example.todoapplication.ViewModel.TaskViewModel

@Composable
fun MainScreenContent(
    userTasksViewModel : TaskViewModel = viewModel(factory = TaskViewModel.Factory)
){
    println("TASK ${userTasksViewModel.userTasks.collectAsState()}")
    val data = userTasksViewModel.taskList.collectAsState().value
    Column {
        MainAppTitle()
        if (!data.isEmpty()){
            LazyColumn {
                items(data){
                    task -> TaskItem(task)
                }
            }
        }
    }

}