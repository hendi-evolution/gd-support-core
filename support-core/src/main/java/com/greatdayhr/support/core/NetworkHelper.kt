package com.greatdayhr.support.core


import com.greatdayhr.support.core.data.BaseResponseV2
import com.greatdayhr.support.core.data.ErrorResponse
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import java.io.IOException
import java.net.SocketTimeoutException


enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

@Throws(Exception::class)
inline fun <reified T> Retrofit.errorConverter(httpException: HttpException): T {
    val converter: Converter<ResponseBody, T> =
        this.responseBodyConverter(T::class.java, arrayOfNulls<Annotation>(0))
    return converter.convert(httpException.response()?.errorBody())!!
}

data class Resource<out T>(val status: Status, val data: T?, val message: String?) {
    companion object {
        fun <T> success(data: T?): Resource<T> {
            return Resource(Status.SUCCESS, data, null)
        }

        fun <T> error(msg: String, data: T?): Resource<T> {
            return Resource(Status.ERROR, data, msg)
        }

        fun <T> loading(data: T?): Resource<T> {
            return Resource(Status.LOADING, data, null)
        }
    }
}

open class ResponseHandler(private val retrofit: Retrofit) {
    fun <T : Any> handleSuccess(data: T): Resource<T> {
        return Resource.success(data)
    }

    fun <T : Any> handleException(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> {
                try {
                    val error = retrofit.errorConverter<ErrorResponse>(e)
                    Resource.error(error.error.message, null)
                } catch (exception: Exception) {
                    Resource.error(
                        getErrorMessage(e.code()),
                        null
                    )
                }
            }
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(900),
                null
            )
            is IOException -> Resource.error(
                getErrorMessage(901),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    fun <T : Any> handleExceptionV2(e: Exception): Resource<T> {
        return when (e) {
            is HttpException -> {
                try {
                    val error = retrofit.errorConverter<BaseResponseV2<Void>>(e)
                    Resource.error(error.message, null)
                } catch (exception: Exception) {
                    Resource.error(
                        getErrorMessage(e.code()),
                        null
                    )
                }
            }
            is SocketTimeoutException -> Resource.error(
                getErrorMessage(900),
                null
            )
            is IOException -> Resource.error(
                getErrorMessage(901),
                null
            )
            else -> Resource.error(getErrorMessage(Int.MAX_VALUE), null)
        }
    }

    private fun getErrorMessage(code: Int): String {
        return when (code) {
            901 -> "Connection error. Please check your network settings."
            900 -> "Timeout"
            401 -> "Unauthorised"
            404 -> "Service not found"
            else -> "Something went wrong"
        }
    }
}