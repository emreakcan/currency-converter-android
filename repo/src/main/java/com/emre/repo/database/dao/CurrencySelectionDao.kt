package com.emre.repo.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.emre.repo.database.entity.CurrencySelectionEntity
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Dao
interface CurrencySelectionDao: BaseDao<CurrencySelectionEntity> {
    @Query("SELECT * FROM CurrencySelectionEntity LIMIT 1")
    fun getLastSelected(): Flowable<CurrencySelectionEntity>

    @Query("UPDATE CurrencySelectionEntity SET code = :code" )
    fun updateCode(code: String): Completable

    @Query("UPDATE CurrencySelectionEntity SET amount = :amount" )
    fun updateAmount(amount: String): Completable
}