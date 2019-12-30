package com.emre.currency.usecase

import com.emre.repo.database.entity.CurrencySelectionEntity
import com.emre.repo.repository.currency.CurrencyLocalRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

class GetLastCurrencyUseCase @Inject constructor(private val currencyLocalRepository: CurrencyLocalRepository) {
    fun get(): Flowable<CurrencySelectionEntity> {
        return currencyLocalRepository.getLastSelectedCurrency()
    }

}