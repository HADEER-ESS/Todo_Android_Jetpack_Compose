package com.example.todoapplication.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapplication.Api.UserRepository
import com.example.todoapplication.data.request.CreateUser
import com.example.todoapplication.data.store.UserStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(
    private val repository: UserRepository,
    private val userStore: UserStore
): ViewModel(){

    private val _isLoggedIn = MutableStateFlow(false)
    val isLoggedIn: StateFlow<Boolean> = _isLoggedIn.asStateFlow()

    private val _registrationSuccess = MutableStateFlow(false)
    val registrationSuccess: StateFlow<Boolean> = _registrationSuccess.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        checkLoginStatus()
    }

    fun createAccount(user: CreateUser){
        viewModelScope.launch {
            _error.value = null
            try {
                val res = repository.createAccount(user)
                if(res.isSuccessful){
                    _registrationSuccess.value = true
                } else {
                    _error.value = "Registration failed"
                }
            } catch (err: Exception){
                _error.value = "Network error: ${err.message}"
            }
        }
    }

    fun login(user: CreateUser){
        viewModelScope.launch {
            _error.value = null
            println("income data $user")
            try {
                val res = repository.login(user)
                println("Fire login $res")
                if(res.isSuccessful){
                    val token = res.body()?.accessToken
                    if (token != null) {
                        userStore.saveToken(token)
                        _isLoggedIn.value = true
                    }
                } else {
                    _error.value = "Login failed"
                }
            } catch (err: Exception){
                println("Error $err")
                _error.value = "Network error: ${err.message}"
            }
        }
    }

    private fun checkLoginStatus() {
        viewModelScope.launch {
            userStore.accessToken.collect { token ->
                _isLoggedIn.value = token.isNotEmpty()
            }
        }
    }

    fun resetRegistrationSuccess() {
        _registrationSuccess.value = false
    }
    
    fun clearError() {
        _error.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                UserViewModel(
                    repository = UserRepository(),
                    userStore = UserStore(application.applicationContext)
                )
            }
        }
    }
}
