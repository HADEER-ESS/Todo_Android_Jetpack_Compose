package com.example.todoapplication.Component

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

@Composable
fun CustomToast(
    context: Context,
    message: String?,
    duration: Int = Toast.LENGTH_SHORT,
    onDismiss: () -> Unit = {}
) {
    LaunchedEffect(message) {
        if (!message.isNullOrBlank()) {
            Toast.makeText(context, message, duration).show()
            onDismiss() // reset the state so the same error can show again
        }
    }
}