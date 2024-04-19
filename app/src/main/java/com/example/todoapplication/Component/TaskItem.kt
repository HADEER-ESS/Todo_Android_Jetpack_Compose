package com.example.todoapplication.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.todoapplication.ViewModel.TaskState
import com.example.todoapplication.data.categoriesItemData
import com.example.todoapplication.ui.theme.TodoApplicationTheme

@Composable
fun TaskItem(
    task : TaskState
){
    val taskColor = categoriesItemData[task.title-1]
    println("Income Task from model ${task.description}")
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .background(color = taskColor.color , shape = RectangleShape)

    ){
        Text(text = task.description)
        Checkbox(checked = task.done, onCheckedChange = {})
    }

}


@Composable
@Preview(backgroundColor = 0xFFFFFFFF)
fun PreviewTaskItem(){

    TodoApplicationTheme {
        TaskItem(TaskState())
    }

}