package com.emre.repo.repository.currency

import com.emre.common.Resource
import com.emre.repo.network.model.RateResponse
import io.reactivex.Observable

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
interface CurrencyRepository {
    fun getLastCurrencies(baseCurrency: String): Observable<Resource<RateResponse>>
}