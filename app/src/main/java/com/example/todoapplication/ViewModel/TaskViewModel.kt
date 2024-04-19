package com.example.todoapplication.ViewModel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.todoapplication.data.categoryItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class Task(val title : categoryItem , val desc : String , val complete : Boolean)

data class TaskState(val title: Int = 0, val description :String = "", var done :Boolean = false )

class TaskViewModel: ViewModel() {

    // Mutable State Flow to hold the user tasks as a list
    //(Mutable State Flow) >> To be modified inside ViewModel class itself
    private var _userTasks = MutableStateFlow<List<TaskState>>(emptyList())

   //(State Flow) >> To be read by other application UI class without making any modifications on it (read only)
    val userTasks : StateFlow<List<TaskState>> = _userTasks.asStateFlow()


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

    fun updateCompleteTask(item : TaskState , doneState: Boolean){
        
        _userTasks.value.find { item.description == it.description }?.let {
            it.done = doneState;
        }
        
    }
}