package com.emre.common.model

/**
 * Created on 2019-10-24.
 * @author EMRE AKCAN
 */
open class BaseException(val originalException: Throwable?) {
    open class UnknownException(originalException: Throwable?) : BaseException(originalException)
    class DatabaseException(originalException: Throwable) : BaseException(originalException)
    //region ..:: Network Exceptions ::..
    open class NetworkWithBodyException(originalException: Throwable, val responseBody: String) :
        BaseException(originalException)

    class UnknownHostException(originalException: Throwable) : BaseException(originalException)
    class TimeoutException(originalException: Throwable) : BaseException(originalException)
    class SSLHandshakeException(originalException: Throwable) : BaseException(originalException)
    class SocketTimeoutException(originalException: Throwable) : BaseException(originalException)
    class ForbiddenSignatureMismatchException(originalException: Throwable, responseBody: String) :
        NetworkWithBodyException(originalException, responseBody)

    class JsonException(originalException: Throwable, responseBody: String) :
        NetworkWithBodyException(originalException, responseBody)


    //endregion
}