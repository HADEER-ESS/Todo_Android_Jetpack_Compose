package com.example.todoapplication.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ViewModel.TaskViewModel
import com.example.todoapplication.data.response.TaskItem
import com.example.todoapplication.data.response.TaskStatus

@Composable
fun TaskItem(
    task: TaskItem,
    token: String,
    userTaskViewModel: TaskViewModel
) {
    var expandList by remember { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 2.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(6.dp))
            .background(Color.White)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(all= 8.dp)
        ){
            Text(text = "title ${task.title}")
            Text(
                text= "DELETE",
                color = Color.Red,
                modifier = Modifier
                    .clickable(
                        onClick = { userTaskViewModel.deleteTask(token, task.id) }
                    )
            )
        }
        Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 2.dp)
            .clickable { expandList = true } // Added clickable to open the menu
    ) {
        Text(text = task.description, modifier = Modifier.padding(start = 8.dp))
        Text(text = task.status.name) // Show current status

        DropdownMenu(
            expanded = expandList,
            onDismissRequest = { expandList = false },
        ) {
            TaskStatus.entries.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name, modifier = Modifier
                        .size(12.dp)
                        .padding(end = 8.dp)) },
                    onClick = {
                        expandList = false
                        println("update function called")
                        userTaskViewModel.updateTaskStatus(token, task.id, status)
                    }
                )
            }
        }
    }
}
}
