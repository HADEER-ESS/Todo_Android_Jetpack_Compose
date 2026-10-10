package com.example.todoapplication.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.ViewModel.TaskViewModel
import com.example.todoapplication.data.request.CreateTask

@Composable
fun MainAppTitle(token: String) {
    var openModel by remember { mutableStateOf(false) }

    Row(
        // Use CenterVertically for better alignment
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding() // This prevents the Status Bar from overlapping the button
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(text = "Welcome Back")
        Button(
            onClick = {
                println("Create Task button clicked")
                openModel = true // Uncommented this line
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF194FF)),
            shape = RoundedCornerShape(16.dp), // Added .dp for consistent rounding
        ) {
            Text(text = "Create Task+")
        }
        
        if (openModel) {
            OpenDialogForm(token, onDismiss = { openModel = false })
        }
    }
}

@Composable
fun OpenDialogForm(
    token: String,
    onDismiss: () -> Unit,
    userTaskViewModel: TaskViewModel = viewModel(factory = TaskViewModel.Factory),
) {
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }

    Dialog(onDismissRequest = onDismiss) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color.Gray)
                .padding(16.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(text = "Create New Task")
                
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Task Title") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3
                )
                
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        onClick = onDismiss,
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            if (title.isNotBlank()) {
                                userTaskViewModel.createNewTask(token, CreateTask(title, description))
                                onDismiss()
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF194FF)),
                    ) {
                        Text(text = "Create")
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun MainTitlePreview() {
    MainAppTitle(token = "sdasdasd")
}
