package com.chris.dictionmaster.util

import com.chris.dictionmaster.model.Definition
import retrofit2.Response

sealed class WordState{
    object onLoading : WordState()
    class onError(val msg:Throwable) : WordState()
    class onSuccess(val data: Response<List<Definition>>) : WordState()
    object Empty : WordState()
}
