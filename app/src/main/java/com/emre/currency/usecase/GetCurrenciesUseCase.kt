package com.emre.currency.usecase

import com.emre.common.Resource
import com.emre.repo.network.model.RateLocalModel
import com.emre.repo.repository.currency.CurrencyRepository
import io.reactivex.Observable
import java.math.BigDecimal
import java.util.*
import javax.inject.Inject

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

class GetCurrenciesUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {

    fun get(
        baseCur: String,
        amount: String
    ): Observable<Resource<List<RateLocalModel>>> {
        return currencyRepository.getLastCurrencies(baseCur)
            .flatMap<Resource<List<RateLocalModel>>> { resource ->
                when (resource) {
                    is Resource.Success -> {
                        val resultList = convertCurrMapToList(
                            resource.data.ratesMap,
                            amount.toBigDecimal()
                        )

                        resultList.add(0,
                            RateLocalModel(
                                Currency.getInstance(baseCur),
                                amount.toBigDecimal()
                            )
                        )
                        Observable.just(Resource.Success(resultList))
                    }
                    is Resource.Error -> Observable.just(Resource.Error(resource.error))
                }
            }
    }


    private fun convertCurrMapToList(
        currMap: Map<String, String>,
        targetAmount: BigDecimal
    ): MutableList<RateLocalModel> {
        val result = arrayListOf<RateLocalModel>()
        currMap.forEach { (key, mapValue) ->
            result.add(
                RateLocalModel(
                    currency = Currency.getInstance(key),
                    amount = mapValue.toBigDecimal() * targetAmount
                )
            )
        }
        return result
    }
}


