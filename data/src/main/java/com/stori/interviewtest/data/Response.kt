package com.stori.interviewtest.data

import com.stori.interviewtest.data.error.ErrorEntity

sealed class Response<out T> {
    object Loading: Response<Nothing>()

    data class Success<out T>(
        val data: T
    ): Response<T>()

    data class Failure(
        val e: ErrorEntity
    ): Response<Nothing>()
}
