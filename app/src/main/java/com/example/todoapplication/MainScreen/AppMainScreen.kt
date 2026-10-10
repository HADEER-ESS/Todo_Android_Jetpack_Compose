package com.example.todoapplication.MainScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.Component.MainAppTitle
import com.example.todoapplication.Component.TaskItem
import com.example.todoapplication.ViewModel.TaskViewModel
import com.example.todoapplication.data.store.UserStore

@Composable
fun MainScreenContent(
    userTasksViewModel : TaskViewModel = viewModel(factory = TaskViewModel.Factory)
){
    val data by userTasksViewModel.taskList.collectAsState()
    val store = UserStore(LocalContext.current)
    val token by store.accessToken.collectAsState(initial = "")
    
    // Call getAllTasks once the screen is rendered and we have a valid token
    LaunchedEffect(token) {
        if (token.isNotBlank()) {
            userTasksViewModel.getAllTasks(token)
        }
//        else{
//            store.clearToken()
//        }
    }

    if(token.isEmpty() || token.isBlank()){
        LoginScreen()
    }
    else {
        Column {
            MainAppTitle(token)
            LazyColumn {
                items(data){ task ->
                    TaskItem(
                        task = task,
                        token = token,
                        userTaskViewModel = userTasksViewModel
                    )
                }
            }
        }
    }
}