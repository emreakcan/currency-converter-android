package com.emre.repo.repository.currency

import com.emre.common.Resource
import com.emre.repo.network.RetrofitApi
import com.emre.repo.network.model.RateResponse
import com.emre.repo.repository.BaseRepository
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

class CurrencyRepositoryImpl @Inject constructor(private val retrofitApi: RetrofitApi) :
    CurrencyRepository, BaseRepository() {
    override fun getLastCurrencies(baseCurrency: String): Observable<Resource<RateResponse>> {
        return retrofitApi.getCurrencies(baseCurrency)
            .sendRequest()
            .toObservable()
    }

}