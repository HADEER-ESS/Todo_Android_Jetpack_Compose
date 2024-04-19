package com.example.todoapplication.data

import androidx.compose.ui.graphics.Color

data class categoryItem(val id:Int, val type : String , val color: Color)

//class CategoriesData {

    val categoriesItemData = listOf(
        categoryItem(
            1,
            "Urgent",
            Color.Red
        ),
        categoryItem(
            2,
            "Important",
            Color.Green
        ),
        categoryItem(
            3,
            "Upcoming",
            Color.Blue
        )
    )
//}