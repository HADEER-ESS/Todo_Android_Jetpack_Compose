package com.example.todoapplication.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
    println("Income Task from model ${task.description}")
    var expandList by remember { mutableStateOf(false) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(color = Color.Cyan, shape = RectangleShape)
            .clickable { expandList = true } // Added clickable to open the menu
    ) {
        Text(text = task.title)
        Text(text = task.description)
        Text(text = task.status.name) // Show current status

        DropdownMenu(
            expanded = expandList,
            onDismissRequest = { expandList = false }
        ) {
            TaskStatus.entries.forEach { status ->
                DropdownMenuItem(
                    text = { Text(status.name) },
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
