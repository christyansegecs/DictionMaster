package com.chris.dictionmaster.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris.dictionmaster.database.models.Word
import com.chris.dictionmaster.repository.WordRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class WordViewModel(private val repository: WordRepository) : ViewModel() {

    @RequiresApi(Build.VERSION_CODES.O)
    fun insert(wordString: String, responseString: String) = viewModelScope.launch {
        repository.insert(wordString, responseString)
    }

    suspend fun getWord(wordString: String): Word? {
        val word = repository.getWord(wordString)
        if (word != null) {
            Log.d("WordViewModel", "Word found: ${word.word}")
        } else {
            Log.d("WordViewModel", "Word not found: $wordString")
        }
        return word
    }

    suspend fun isWordLimitReached(date: LocalDate): Boolean {
        return repository.countWordsByDate(date) >= 10
    }
}
