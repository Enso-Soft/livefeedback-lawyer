package com.lafi.lawyer.core.model.common.data

data class ErrorData(
    val code: Int,
    val message: String,
    val errorType: String = ""
)