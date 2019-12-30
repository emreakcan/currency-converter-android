package com.emre.repo.repository

import com.emre.common.model.BaseException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import com.google.gson.stream.MalformedJsonException
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
class ErrorHandler @Inject constructor(

) {
    fun getError(throwable: Throwable): BaseException {

        return when (throwable) {

            is HttpException -> {
                val errorBodyStr = throwable.response()?.errorBody()?.string() ?: ""
                BaseException.NetworkWithBodyException(throwable, errorBodyStr)
            }

            is MalformedJsonException, is JsonParseException, is JsonSyntaxException -> BaseException.JsonException(
                throwable,
                ""
            )
            is UnknownHostException -> {
                BaseException.UnknownHostException(throwable)
            }
            is TimeoutException -> BaseException.TimeoutException(throwable)
            is SSLHandshakeException -> BaseException.SSLHandshakeException(throwable)
            is SocketTimeoutException -> BaseException.SocketTimeoutException(throwable)
            is IOException -> BaseException.UnknownException(throwable)
            else -> BaseException.UnknownException(throwable)
        }
    }
}