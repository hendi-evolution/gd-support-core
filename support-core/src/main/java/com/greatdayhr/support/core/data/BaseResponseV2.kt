package com.greatdayhr.support.core.data

import com.google.gson.annotations.SerializedName

data class BaseResponseV2<T>(
    @SerializedName("message")
    val message: String,
    @SerializedName("page")
    var page: Int,
    @SerializedName("limit")
    var limit: Int,
    @SerializedName("total")
    var total: Int,
    @SerializedName("totalPage")
    var totalPage: Int,
    @SerializedName("data")
    val data: T
)
