package com.chris.dictionmaster.di.modules

import com.chris.dictionmaster.api.DictionaryAPI
import com.chris.dictionmaster.database.WordRoomDatabase
import com.chris.dictionmaster.repository.DictionaryAPIRepository
import com.chris.dictionmaster.repository.DictionaryAPIRepositoryImpl
import com.chris.dictionmaster.repository.WordRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val repositoriesModule = module {
    single<CoroutineScope> { CoroutineScope(SupervisorJob() + Dispatchers.IO) }
    single<DictionaryAPIRepository> { provideDictionaryAPIRepository(get()) }
    single { WordRoomDatabase.getDatabase(get(), androidApplication()) }
    single { get<WordRoomDatabase>().wordDao() }
    single { WordRepository(get()) }
}

fun provideDictionaryAPIRepository(dictionaryAPI: DictionaryAPI) : DictionaryAPIRepositoryImpl {
    return DictionaryAPIRepositoryImpl(
        dictionaryAPI
    )
}