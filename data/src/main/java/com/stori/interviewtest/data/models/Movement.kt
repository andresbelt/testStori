package com.stori.interviewtest.data.models

data class Movement(var type: String? = null, var total: String? = null, var description: String? = null)

data class MovementsList(val transactions: List<Movement>? = null, val timestamp: String? = null)
