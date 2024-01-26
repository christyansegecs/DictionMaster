package com.chris.dictionmaster.api

import com.chris.dictionmaster.model.Definition
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface DictionaryAPI {

    @GET("api/v2/entries/en/{word}")
    suspend fun getDefinition(@Path("word") word: String): Response<List<Definition>>
}