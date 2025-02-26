package com.evening.counter.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class TableItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val column1: String,
    val column2: String,
    val column3: String
)