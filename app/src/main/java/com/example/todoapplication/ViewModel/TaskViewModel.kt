package com.example.todoapplication.ViewModel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapplication.Api.TaskRepository
import com.example.todoapplication.data.categoryItem
import com.example.todoapplication.data.request.CreateTask
import com.example.todoapplication.data.response.TaskItem
import com.example.todoapplication.data.response.TaskStatus
import com.example.todoapplication.data.store.UserStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class Task(val title : categoryItem , val desc : String , val complete : Boolean)

data class TaskState(
    val title: String = "",
    val description :String = "",
    var status: categoryItem
)

class TaskViewModel(
    private val repository: TaskRepository,
    private val userStore: UserStore
): ViewModel() {
    // Mutable State Flow to hold the user tasks as a list
    //(Mutable State Flow) >> To be modified inside ViewModel class itself
    private var _userTasks = MutableStateFlow<List<TaskState>>(emptyList())
    private var _taskList = MutableStateFlow<List<TaskItem>>(emptyList())
    

   //(State Flow) >> To be read by other application UI class without making any modifications on it (read only)
    val userTasks : StateFlow<List<TaskState>> = _userTasks.asStateFlow()
    val taskList : StateFlow<List<TaskItem>> = _taskList.asStateFlow()
    var taskViewData: TaskItem? = null


    //Create Helper function to update ot add tasks in _userTasks Mutable State Flow
    fun addToTask(token: String, item : TaskState ){
        println("Print $item")
        // Get the current list of tasks
        val currentTask = _userTasks.value.toMutableList()
        // Add the new task to the list
        currentTask.add(item)
        // Update _userTasks with the new list of tasks
        _userTasks.value = currentTask
    }

    //API <-> connection
     fun createNewTask(token: String, item: CreateTask){
        viewModelScope.launch{
            try {
                val res = repository.createTask(token, body = item)
                if (res.isSuccessful) {
                    println("Task created successfully: ${res.body()}")
                    getAllTasks(token)
                } else {
                    println("Task creation failed: ${res.code()} ${res.message()}")
                    if (res.code() == 401) {
                        println("User is not authorized - clearing token")
                        userStore.clearToken()
                    }
                }
            }catch (err: Exception){
                println("Error in creating task $err")
            }
        }
    }

    fun updateTaskStatus(token: String, id : String , status: TaskStatus){
        viewModelScope.launch {
            try {
                val res = repository.updateTaskStatus(token, id, status)
                if (res.isSuccessful) {
                    getAllTasks(token)
                } else if (res.code() == 401) {
                    userStore.clearToken()
                }
            }catch (err: Exception){
                println("Error in Update task status $err")
            }
        }
    }

    fun getAllTasks(token: String){
        viewModelScope.launch {
            try {
                val res = repository.getTasks(token)
                if (res.isSuccessful) {
                    _taskList.value = res.body()?.toMutableList() ?: mutableListOf()
                } else if (res.code() == 401) {
                    userStore.clearToken()
                }
            }catch (err: Exception){
                println("Error in Getting task List $err")
            }
        }
    }

    fun deleteTask(token: String,id: String){
        viewModelScope.launch {
            try {
                val res = repository.deleteTask(token, id)
                if(res.isSuccessful){
                    getAllTasks(token)
                } else if (res.code() == 401) {
                    userStore.clearToken()
                }
            }catch (err: Exception){
                println("Error in Delete task $err")
            }
        }
    }

    fun getTaskById(token: String, id: String){
        viewModelScope.launch {
            try {
                val res = repository.getTaskById(token, id)
                if (res.isSuccessful) {
                    taskViewData = res.body()
                } else if (res.code() == 401) {
                    userStore.clearToken()
                }
            }catch (err: Exception){
                println("Error in Getting task by id $err")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY] as Application
                TaskViewModel(
                    repository = TaskRepository(),
                    userStore = UserStore(application)
                )
            }
        }
    }
}
