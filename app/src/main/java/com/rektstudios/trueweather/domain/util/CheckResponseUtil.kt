package com.rektstudios.trueweather.domain.util

import retrofit2.Response

class CheckResponseUtil<T>(
    private val response: Response<T>,
) {
    fun checkResponse(): Resource<T> =
        if (response.isSuccessful) {
            response.body()?.let {
                Resource.Success(it)
            } ?: Resource.Error("Empty body")
        } else {
            if (response.code().toString().startsWith("5")) {
                Resource.Error("Server error")
            } else {
                Resource.Error(response.errorBody()?.string() ?: "Default Error")
            }
        }
}
