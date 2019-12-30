package com.emre.currency.usecase

import com.emre.repo.repository.currency.CurrencyLocalRepository
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import javax.inject.Inject

class UpdateCurrencyCodeUseCase @Inject constructor(private val currencyLocalRepository: CurrencyLocalRepository){
    fun setAmount(amount: BigDecimal): Completable {
        return currencyLocalRepository
            .setAmount(amount.toPlainString())
            .subscribeOn(Schedulers.io())
    }

    fun setCode(code: String): Completable {
        return currencyLocalRepository
            .setCurrencyCode(code)
            .subscribeOn(Schedulers.io())
    }
}