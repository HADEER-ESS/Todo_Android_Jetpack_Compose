package com.example.todoapplication.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.todoapplication.Api.TaskRepository
import com.example.todoapplication.data.categoryItem
import com.example.todoapplication.data.request.CreateTask
import com.example.todoapplication.data.response.TaskItem
import com.example.todoapplication.data.response.TaskStatus
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
    fun addToTask(item : TaskState ){

        println("Print $item")
//        if(item.title == 0){
//            Toast.makeText(context , "Please select task category" , Toast.LENGTH_LONG)
//        }

        // Get the current list of tasks
        val currentTask = _userTasks.value.toMutableList()

        // Add the new task to the list
        currentTask.add(item)

        // Update _userTasks with the new list of tasks
        _userTasks.value = currentTask
    }

    //API <-> connection
     fun createNewTask(item: CreateTask){
        viewModelScope.launch{
            try {
                val res = repository.createTask(body = item)
            }catch (err: Exception){
                println("Error in creating task $err",)
            }
        }
    }

    fun updateTaskStatus(id : String , status: TaskStatus){
        viewModelScope.launch {
            try {
                val res = repository.updateTaskStatus(id, status)
                _taskList.value.find{ res.body()?.id == it.id}?.let {
                    it.status = status
                }
            }catch (err: Exception){
                println("Error in Update task status $err")
            }
        }
    }

    fun getAllTasks(){
        viewModelScope.launch {
            try {
                val res = repository.getTasks()
                _taskList.value = res.body()!!.toMutableList()
            }catch (err: Exception){
                println("Error in Getting task List $err")
            }
        }
    }

    fun deleteTask(id: String){
        viewModelScope.launch {
            try {
                val res = repository.deleteTask(id)
                if(res.isSuccessful){
                    getAllTasks()
                }
            }catch (err: Exception){
                println("Error in Delete task $err")
            }
        }
    }

    fun getTaskById(id: String){
        viewModelScope.launch {
            try {
                val res = repository.getTaskById(id)
                taskViewData = res.body()

            }catch (err: Exception){
                println("Error in Getting task by id $err")
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                TaskViewModel(repository = TaskRepository())
            }
        }
    }
}