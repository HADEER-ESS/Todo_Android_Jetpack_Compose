package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.MainScreen.LoginScreen
import com.example.todoapplication.MainScreen.MainScreenContent
import com.example.todoapplication.ViewModel.UserViewModel
import com.example.todoapplication.ui.theme.TodoApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TodoApplicationTheme {
                val userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)
                val isLoggedIn by userViewModel.isLoggedIn.collectAsState()

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn) {
                        MainScreenContent()
                    } else {
                        LoginScreen(userViewModel = userViewModel)
                    }
                }
            }
        }
    }
}
