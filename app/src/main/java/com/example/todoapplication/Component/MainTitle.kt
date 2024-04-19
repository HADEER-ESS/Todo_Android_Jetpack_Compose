package com.example.todoapplication.Component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import android.widget.Toast
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.todoapplication.ViewModel.TaskState
import com.example.todoapplication.ViewModel.TaskViewModel
import com.example.todoapplication.data.categoriesItemData
import com.example.todoapplication.data.categoryItem

@Composable
fun MainAppTitle(

){

    var openModel = remember {
        mutableStateOf(false)
    }

    Row (
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = 16.dp)

    ){
        Text(text = "Welcome Back")
        Button(
            onClick = { openModel.value = true},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF194FF)),
            shape = RoundedCornerShape(16),
        ) {
            Text(text = "+")
        }
        if (openModel.value){
            openDialogForm ( openModel )
        }
    }
}

@Composable
fun openDialogForm(
    closeDialog:MutableState<Boolean>,
    userTaskViewModel : TaskViewModel = viewModel(),
){

    var selectedCategoryType by rememberSaveable { mutableIntStateOf(0) }
    var taskInfo by rememberSaveable { mutableStateOf<String>("") }

    Dialog(
        onDismissRequest = {
            println("Dismiss Clicked")
            closeDialog.value = false
        }
        ) {
        Box(
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .background(Color.White)
                .height(200.dp)
                .border(width = 1.dp, shape = RoundedCornerShape(10.dp), color = Color.Gray)

        ){
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(4.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                BasicTextField(
                    value = taskInfo,
                    onValueChange = {value -> taskInfo = value},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .border(
                            width = 1.dp,
                            shape = RoundedCornerShape(5.dp),
                            color = Color.Gray
                        )

                )
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 18.dp, horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ){
                    items(categoriesItemData){
                            category -> TaskCategoryType(
                                            data = category,
                                            update = {newVal -> selectedCategoryType = newVal},
                                            selected = selectedCategoryType
                                        )
                    }
                }
                Button(
                    onClick = {
                        closeDialog.value = false
                        userTaskViewModel.addToTask(TaskState(selectedCategoryType ,taskInfo , false ))
                    },
                    shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF194FF)),
                )
                {
                    Text(text = "Add")
                }

            }
        }


    }
}
@Composable
fun TaskCategoryType(
    data : categoryItem,
    update : (Int) -> Unit,
    selected : Int
){

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable { update(data.id) }
            .background(
                shape = RectangleShape,
                color = if (selected == data.id) {
                    Color.Gray
                } else {
                    Color.Transparent
                }
            )
    ){
        Spacer(modifier = Modifier
            .clip(CircleShape)
            .background(data.color)
            .height(20.dp)
            .width(20.dp)
        )
        Text(
            text = data.type ,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )
    }

}

@Composable
@Preview(backgroundColor = 0xFFFFFFF)
fun ModelPreview(){
    var close = remember {
        mutableStateOf(true)
    }
    openDialogForm(close)
}

@Composable
@Preview
fun MainTitlePreview(){
    MainAppTitle()
}