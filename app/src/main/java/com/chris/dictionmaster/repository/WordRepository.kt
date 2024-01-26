package com.chris.dictionmaster.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.chris.dictionmaster.database.daos.WordDao
import com.chris.dictionmaster.database.models.Word
import java.time.LocalDate

class WordRepository(private val wordDao: WordDao) {

    suspend fun getWord(wordString: String): Word? {
        return wordDao.getWord(wordString)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun insert(word: String, response: String) {
        val wordWithDate = Word(word, response, LocalDate.now())
        wordDao.insert(wordWithDate)
    }

    suspend fun countWordsByDate(date: LocalDate): Int {
        return wordDao.countWordsByDate(date)
    }
}
