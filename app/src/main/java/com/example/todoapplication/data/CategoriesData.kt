package com.example.todoapplication.data

import androidx.compose.ui.graphics.Color

data class categoryItem(val id:Int, val type : String , val color: Color)

//class CategoriesData {

    val categoriesItemData = listOf(
        categoryItem(
            1,
            "OPEN",
            Color.Red
        ),
        categoryItem(
            2,
            "IN_PROGRESS",
            Color.Green
        ),
        categoryItem(
            3,
            "DONE",
            Color.Blue
        )
    )
//}