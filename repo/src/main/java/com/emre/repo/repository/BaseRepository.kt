package com.emre.repo.repository

import com.emre.common.Resource
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
abstract class BaseRepository {
    @Inject
    protected lateinit var errorHandler: ErrorHandler

    protected fun <T : Any> Single<T>.sendRequest(): Single<Resource<T>> {
        return this
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .map<Resource<T>> { Resource.Success(it) }
            .onErrorReturn { error: Throwable ->
                return@onErrorReturn Resource.Error(errorHandler.getError(error))
            }
    }
}