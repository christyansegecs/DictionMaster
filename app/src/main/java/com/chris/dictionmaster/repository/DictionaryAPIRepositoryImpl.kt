package com.chris.dictionmaster.repository

import com.chris.dictionmaster.model.Definition
import com.chris.dictionmaster.api.DictionaryAPI
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response

class DictionaryAPIRepositoryImpl(
    private val dictionaryAPI: DictionaryAPI,

    ) : DictionaryAPIRepository {

    override suspend fun getDefinition(word: String): Flow<Response<List<Definition>>> = flow {
        emit(dictionaryAPI.getDefinition(word))
    }.flowOn(Dispatchers.IO)


}