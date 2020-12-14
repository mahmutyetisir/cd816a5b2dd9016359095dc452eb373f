package com.myetisir.spacetransporter.data.repository.ssot

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.myetisir.spacetransporter.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

fun <T> resultLiveData(networkCall: suspend () -> Resource<T>): LiveData<Resource<T>> =
    liveData(Dispatchers.IO) {
        emit(Resource.Loading)
        emit(networkCall.invoke())
    }

fun <T> resultFlow(networkCall: suspend () -> Resource<T>): Flow<Resource<T>> {
    return flow {
        emit(Resource.Loading)
        emit(networkCall.invoke())
    }.flowOn(Dispatchers.IO)
}