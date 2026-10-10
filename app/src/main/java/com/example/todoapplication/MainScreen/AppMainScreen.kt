package com.example.todoapplication.MainScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.Component.MainAppTitle
import com.example.todoapplication.Component.TaskItem
import com.example.todoapplication.ViewModel.TaskViewModel
import com.example.todoapplication.data.store.UserStore
import kotlinx.coroutines.launch

@Composable
fun MainScreenContent(
    userTasksViewModel : TaskViewModel = viewModel(factory = TaskViewModel.Factory)
){
    val data by userTasksViewModel.taskList.collectAsState()
    val store = UserStore(LocalContext.current)
    val token by store.accessToken.collectAsState(initial = "")
    val scope = rememberCoroutineScope()
    // Call getAllTasks once the screen is rendered and we have a valid token
    LaunchedEffect(token) {
        if (token.isNotBlank()) {
            userTasksViewModel.getAllTasks(token)
        }
    }

    if(token.isEmpty() || token.isBlank()){
        LoginScreen()
    }
    else {
        Column(
            verticalArrangement = Arrangement.SpaceBetween
        ){
            MainAppTitle(token)
            LazyColumn(
                modifier = Modifier.weight(1f)
            ){
                items(data){ task ->
                    TaskItem(
                        task = task,
                        token = token,
                        userTaskViewModel = userTasksViewModel
                    )
                }
            }
            Button(
                modifier = Modifier
                    .align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF194FF)),
                shape = RoundedCornerShape(16.dp), // Added .dp for consistent rounding
                onClick = {
                    scope.launch {
                        store.clearToken()
                    }
                }
            ) {
                Text(
                    text ="Log Out",
                    color = Color.White
                )
            }
        }
    }
}