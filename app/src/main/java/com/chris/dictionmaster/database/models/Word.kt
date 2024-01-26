package com.chris.dictionmaster.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "word_table")
data class Word (
    @PrimaryKey val word: String,
    val response: String,
    val dateAdded: LocalDate
)
