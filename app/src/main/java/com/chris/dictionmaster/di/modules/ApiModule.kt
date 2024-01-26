package com.chris.dictionmaster.di.modules

import com.chris.dictionmaster.api.DictionaryAPI
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val apiModule = module {
    single { provideDictionaryAPI() }
}

fun provideDictionaryAPI(): DictionaryAPI {
    return Retrofit.Builder()
        .baseUrl("https://api.dictionaryapi.dev/")
        .addConverterFactory(GsonConverterFactory.create())
        .build().create(DictionaryAPI::class.java)
}