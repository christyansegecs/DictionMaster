package com.chris.dictionmaster.repository

import com.chris.dictionmaster.model.Definition
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface DictionaryAPIRepository {

    suspend fun getDefinition(word: String): Flow<Response<List<Definition>>>

}