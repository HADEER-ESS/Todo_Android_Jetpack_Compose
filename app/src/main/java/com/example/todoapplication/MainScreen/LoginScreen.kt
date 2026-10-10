package com.example.todoapplication.MainScreen

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.TextField
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.Component.CustomToast
import com.example.todoapplication.ViewModel.UserViewModel
import com.example.todoapplication.data.request.CreateUser

@Composable
fun LoginScreen(
    userViewModel: UserViewModel = viewModel(factory = UserViewModel.Factory)
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var firstTime by rememberSaveable { mutableStateOf(false) }
    
    val registrationSuccess by userViewModel.registrationSuccess.collectAsState()
    val context = LocalContext.current

    // Observe registration success to switch back to the login view
    LaunchedEffect(registrationSuccess) {
        if (registrationSuccess) {
            Toast.makeText(context, "Account Created Successfully!", Toast.LENGTH_SHORT).show()
            firstTime = false
            userViewModel.resetRegistrationSuccess()
        }
    }

    val defineView: String = if(firstTime) "Create Account" else "Login"

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = email,
            onValueChange = {email = it},
            label = { Text(text = "Email")},
            placeholder = { Text(text = "Enter Email")},
            singleLine = true
        )
        Spacer(modifier = Modifier.height(24.dp))
        TextField(
            value = password,
            onValueChange = {password = it},
            label = { Text(text = "Password")},
            placeholder = { Text(text = "Enter Password")},
            singleLine = true,
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(modifier = Modifier.height(33.dp))
        Button(onClick = {
            if(email.isNotEmpty() && password.isNotEmpty()){
                if(firstTime){
                    userViewModel.createAccount(CreateUser(email, password))
                } else {
                    userViewModel.login(CreateUser(email, password))
                }
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text(text= defineView)
        }
        
        if(!firstTime){
            Row() {
                Text(text = "Don't have account.")
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text= "Create New One",
                    modifier = Modifier.clickable(onClick = { firstTime = true })
                )
            }
        } else {
            Text(
                text= "Back to Login",
                modifier = Modifier.clickable(onClick = { firstTime = false })
            )
        }
        CustomToast(
            context = context,
            message = userViewModel.error.collectAsState().value,
            onDismiss = userViewModel::clearError
        )
    }
}
