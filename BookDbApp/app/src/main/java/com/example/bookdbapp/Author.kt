package com.example.bookdbapp

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Index

@Entity(tableName = "authors", indices = [Index(value = ["name"], unique = true)])
data class Author(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String
)
