package com.greatdayhr.support.core.data

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("error")
    val error: Error
)

data class Error(
    @SerializedName("message")
    val message: String,
    @SerializedName("status")
    val status: Int
)