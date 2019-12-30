package com.emre.repo.repository.currency

import com.emre.repo.database.entity.CurrencySelectionEntity
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
interface CurrencyLocalRepository {
    fun getLastSelectedCurrency(): Flowable<CurrencySelectionEntity>
    fun setCurrencyCode(code: String): Completable
    fun setAmount(amount: String): Completable

}