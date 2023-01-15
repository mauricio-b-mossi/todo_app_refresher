package com.example.todoapprefresher.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val title : String,
    val description : String = "",
    @PrimaryKey val id : Int? = null
)
