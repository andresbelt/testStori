package com.stori.interviewtest.data.error

data class ErrorEntity(
    val throwable: Throwable? = null,
    val id: String? = null,
    val message: String = ""
)
