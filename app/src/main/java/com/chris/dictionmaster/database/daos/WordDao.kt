package com.chris.dictionmaster.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chris.dictionmaster.database.models.Word
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

@Dao
interface WordDao {

    @Query("SELECT * FROM word_table ORDER BY word ASC")
    fun getAlphabetizedWords(): Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("SELECT COUNT(*) FROM word_table WHERE dateAdded = :date")
    suspend fun countWordsByDate(date: LocalDate): Int

    @Query("SELECT * FROM word_table WHERE word = :word")
    suspend fun getWord(word: String): Word?
}
