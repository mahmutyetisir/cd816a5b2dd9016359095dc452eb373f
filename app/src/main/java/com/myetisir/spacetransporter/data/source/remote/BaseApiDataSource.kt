package com.myetisir.spacetransporter.data.source.remote

import android.content.Context
import com.myetisir.spacetransporter.common.isOnline
import com.myetisir.spacetransporter.util.Resource
import retrofit2.Response

abstract class BaseDataSource(private val context: Context) {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {

        return if (context.isOnline()) {
            try {
                val response = call()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) return Resource.Success(body)
                }
                return error(" ${response.code()} ${response.message()}")
            } catch (e: Exception) {
                return error(e.localizedMessage ?: e.toString())
            }
        } else {
            Resource.Error(ConnectionError("hata! internet"))
        }
    }

    private fun <T> error(message: String): Resource<T> {
        return Resource.Error(Throwable("Hata: $message"))
    }
}

class ConnectionError(descriptionMessage: String) : Throwable(descriptionMessage) {}