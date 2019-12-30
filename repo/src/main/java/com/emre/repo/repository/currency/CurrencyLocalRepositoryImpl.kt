package com.emre.repo.repository.currency

import com.emre.repo.database.dao.CurrencySelectionDao
import com.emre.repo.database.entity.CurrencySelectionEntity
import com.emre.repo.repository.BaseRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
class CurrencyLocalRepositoryImpl @Inject constructor(
    private val currencySelectionDao: CurrencySelectionDao
) : CurrencyLocalRepository, BaseRepository() {
    override fun getLastSelectedCurrency(): Flowable<CurrencySelectionEntity> {
        return currencySelectionDao.getLastSelected()
    }

    override fun setCurrencyCode(code: String): Completable {
        return currencySelectionDao.updateCode(code)
    }

    override fun setAmount(amount: String): Completable {
        return currencySelectionDao.updateAmount(amount)
    }

}