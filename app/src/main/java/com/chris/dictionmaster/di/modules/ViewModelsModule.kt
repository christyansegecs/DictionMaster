package com.chris.dictionmaster.di.modules

import com.chris.dictionmaster.repository.DictionaryAPIRepository
import com.chris.dictionmaster.repository.WordRepository
import com.chris.dictionmaster.viewmodel.DictionaryViewModel
import com.chris.dictionmaster.viewmodel.WordViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelsModule = module {
    viewModel { provideDictionaryAPIViewModel(get()) }
    viewModel { provideWordViewModel(get()) }
}

fun provideDictionaryAPIViewModel(dictionaryAPIRepository: DictionaryAPIRepository): DictionaryViewModel {
    return DictionaryViewModel(
        dictionaryAPIRepository
    )
}

fun provideWordViewModel(wordRepository: WordRepository): WordViewModel {
    return WordViewModel(wordRepository)
}