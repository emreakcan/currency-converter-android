package com.emre.repo.network

import com.emre.repo.network.model.RateResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
interface RetrofitApi {
    @GET("latest")
    fun getCurrencies(@Query("base") baseCurrency: String): Single<RateResponse>
}