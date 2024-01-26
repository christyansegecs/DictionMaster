package com.chris.dictionmaster.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chris.dictionmaster.repository.DictionaryAPIRepository
import com.chris.dictionmaster.util.WordState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class DictionaryViewModel(
    private val repository: DictionaryAPIRepository
) : ViewModel() {

    private val _wordStateFlow = MutableStateFlow<WordState>(WordState.Empty)
    val wordStateFlow: StateFlow<WordState> = _wordStateFlow

    fun getDefinition(word: String) = viewModelScope.launch {
        repository.getDefinition(word)
            .onStart { _wordStateFlow.value = WordState.onLoading }
            .catch { e -> _wordStateFlow.value = WordState.onError(e) }
            .collect { data -> _wordStateFlow.value = WordState.onSuccess(data) }
    }
}