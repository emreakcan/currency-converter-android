package com.emre.currency.ui.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import com.emre.common.Resource
import com.emre.currency.ui.base.BaseViewModel
import com.emre.currency.usecase.GetCurrenciesUseCase
import com.emre.currency.usecase.GetLastCurrencyUseCase
import com.emre.currency.usecase.UpdateCurrencyCodeUseCase
import com.emre.currency.util.extension.CurrencyDiff
import com.emre.repo.network.model.RateLocalModel
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.math.BigDecimal
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
class MainViewModel @Inject constructor(
    private val getCurrenciesUseCase: GetCurrenciesUseCase,
    private val updateCurrencyCodeUseCase: UpdateCurrencyCodeUseCase,
    private val getLastCurrencyUseCase: GetLastCurrencyUseCase
) : BaseViewModel() {

    val currenciesLD = MutableLiveData<List<RateLocalModel>>()
    var errorVisibilityLD = MutableLiveData<Int>().apply {
        View.GONE
    }

    private var userCurrencyExchangeRatesListChangesDisposable: Disposable? = null

    fun getCurrencies(baseCur: String, amount: String, repeat: Boolean) {
        userCurrencyExchangeRatesListChangesDisposable?.dispose()

        val observable = getCurrenciesUseCase.get(baseCur, amount)
        if(repeat)
            observable.repeatWhen { it.delay(1, TimeUnit.SECONDS) }

        observable
            .throttleWithTimeout(200, TimeUnit.MILLISECONDS)
            .subscribe { resource ->
                isDataLoading.postValue(false)
                when (resource) {
                    is Resource.Success -> {
                        currenciesLD.postValue(resource.data)
                        errorVisibilityLD.postValue(View.GONE)
                    }
                    is Resource.Error -> {
                        errorVisibilityLD.postValue(View.VISIBLE)
                        errorMessage.postValue(resource.error)
                    }
                }
            }
            .let { userCurrencyExchangeRatesListChangesDisposable = it }

    }

    fun onCurrencyItemSelected(rateLocalModel: RateLocalModel) {
        updateCurrencyCodeUseCase
            .setCode(rateLocalModel.currency.currencyCode)
            .subscribe()
            .let { compositeDisposable.add(it) }
    }

    fun onCurrencyAmountChanged(newCurrencyAmount: BigDecimal) {
        updateCurrencyCodeUseCase
            .setAmount(newCurrencyAmount)
            .subscribe()
    }

    fun onCalculateExchangeRateListsDifferences(
        oldList: List<RateLocalModel>,
        newList: List<RateLocalModel>
    ): Single<DiffUtil.DiffResult> {
        return Single.just(DiffUtil.calculateDiff(CurrencyDiff(oldList, newList)))
            .subscribeOn(Schedulers.io())
    }

    fun observeLastSelectedCurrency() {
        errorVisibilityLD.postValue(View.GONE)
        isDataLoading.postValue(true)
        getLastCurrencyUseCase
            .get()
            .subscribe {
                getCurrencies(it.code, it.amount, true)
            }
            .let { compositeDisposable.add(it) }
    }
}