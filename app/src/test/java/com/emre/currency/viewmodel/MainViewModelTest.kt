package com.emre.currency.viewmodel

import androidx.lifecycle.Observer
import com.emre.common.Resource
import com.emre.common.model.BaseException
import com.emre.currency.BaseTest
import com.emre.currency.rules.InstantExecutorExtension
import com.emre.currency.rules.TestSchedulerExtension
import com.emre.currency.ui.main.MainViewModel
import com.emre.currency.usecase.GetCurrenciesUseCase
import com.emre.currency.usecase.GetLastCurrencyUseCase
import com.emre.currency.usecase.UpdateCurrencyCodeUseCase
import com.emre.repo.network.model.RateLocalModel
import com.emre.repo.repository.currency.CurrencyLocalRepository
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import io.reactivex.Completable
import io.reactivex.Observable
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@ExtendWith(value = [InstantExecutorExtension::class, TestSchedulerExtension::class])
class MainViewModelTest : BaseTest() {

    @MockK
    lateinit var getCurrenciesUseCase: GetCurrenciesUseCase
    lateinit var mainViewModel: MainViewModel

    @MockK
    lateinit var get: GetCurrenciesUseCase

    @MockK
    lateinit var getLastCurrencyUseCase: GetLastCurrencyUseCase

    @MockK
    lateinit var updateCurrenciesUseCase: UpdateCurrencyCodeUseCase

    private lateinit var isDataLoadingObserver: Observer<Boolean>
    private val unknownHostExceptionText = "No internet connection"
    private val baseCurr = "AUD"

    @MockK
    lateinit var localService: CurrencyLocalRepository

    @BeforeEach
    override fun setUp() {
        super.setUp()
        this.mainViewModel = MainViewModel(this.getCurrenciesUseCase, this.updateCurrenciesUseCase, this.getLastCurrencyUseCase)

        subscribeLoadingLD()
    }

    private fun subscribeLoadingLD() {
        isDataLoadingObserver = spyk(Observer {})
        this.mainViewModel.isDataLoading.observeForever(isDataLoadingObserver)
    }

    @Test
    @DisplayName(
        "When invoked CurrencyList, " +
                "Given Empty RateModel model, " +
                "Returned null LiveData"
    )
    fun rateListReturnNull() {
        //region ..:: Given ::..
        val mockedObserver: Observer<List<RateLocalModel>> = spyk(Observer {})
        this.mainViewModel.currenciesLD.observeForever(mockedObserver)

        every {
            getCurrenciesUseCase.get(baseCurr, 10.5.toString())
        } returns Observable.just(Resource.Success(createDummyEmptyRateLocalModelList()))
        //endregion

        //region ..:: When ::..
        this.mainViewModel.getCurrencies(baseCurr, 10.5.toString(), false)
        //endregion

        //region ..:: Then ::..
        val observed = mutableListOf<List<RateLocalModel>>()
        val observedLoading = mutableListOf<Boolean>()
        verify {
            mockedObserver.onChanged(capture(observed))
            isDataLoadingObserver.onChanged(capture(observedLoading))
        }

        Truth.assertThat(observed[0]).isEmpty()
        //endregion
    }

    @Test
    @DisplayName(
        "When invoked CurrencyList, " +
                "Given Empty RateModel model, " +
                "Returned non-null LiveData"
    )
    fun rateListReturnNonNull() {
        //region ..:: Given ::..
        val mockedObserver: Observer<List<RateLocalModel>> = spyk(Observer {})
        this.mainViewModel.currenciesLD.observeForever(mockedObserver)

        every {
            getCurrenciesUseCase.get(baseCurr, 10.5.toString())
        } returns Observable.just(Resource.Success(createDummyRateLocalModelList()))
        //endregion

        //region ..:: When ::..
        this.mainViewModel.getCurrencies(baseCurr, 10.5.toString(), false)
        //endregion

        //region ..:: Then ::..
        val observed = mutableListOf<List<RateLocalModel>>()
        val observedLoading = mutableListOf<Boolean>()
        verify {
            mockedObserver.onChanged(capture(observed))
            isDataLoadingObserver.onChanged(capture(observedLoading))
        }

        Truth.assertThat(observed[0]).isNotEmpty()
        Truth.assertThat(observed[0][0].currency.currencyCode).isEqualTo("TRY")
        Truth.assertThat(observed[0][0].amount.setScale(2, RoundingMode.CEILING))
            .isEqualTo(BigDecimal(1.63).setScale(2, RoundingMode.CEILING))
        Truth.assertThat(observedLoading[0]).isEqualTo(false)
        //endregion
    }

    @Test
    @DisplayName("When base currency changed, get correct base currency and correctly observed")
    fun testBaseCurrencyChanges(){

        every { localService.setCurrencyCode("EUR") } returns Completable.complete()


        every {
            localService.getLastSelectedCurrency().subscribe {
                Truth.assertThat(it.code).isEqualTo("EUR")
            }
        }

    }

    @Test
    @DisplayName(
        "When invoked CurrencyList, " +
                "Given Empty RateModel model, " +
                "Returned error"
    )
    fun rateListReturnError() {
        //region ..:: Given ::..
        val mockedObserver: Observer<BaseException> = spyk(Observer {})
        this.mainViewModel.errorMessage.observeForever(mockedObserver)

        every {
            getCurrenciesUseCase.get(baseCurr, 10.5.toString())
        } returns Observable.just(
            Resource.Error(
                BaseException.UnknownException(
                    Exception(
                        unknownHostExceptionText
                    )
                )
            )
        )
        //endregion

        //region ..:: When ::..
        this.mainViewModel.getCurrencies(baseCurr, 10.5.toString(), false)
        //endregion

        //region ..:: Then ::..
        val observed = mutableListOf<BaseException>()
        val observedLoading = mutableListOf<Boolean>()
        verify {
            mockedObserver.onChanged(capture(observed))
            isDataLoadingObserver.onChanged(capture(observedLoading))
        }

        Truth.assertThat(observed[0]).isNotNull()
        Truth.assertThat(observed[0].originalException?.message).isEqualTo(unknownHostExceptionText)
        Truth.assertThat(observedLoading[0]).isEqualTo(false)
        //endregion
    }


    private fun createDummyEmptyRateLocalModelList(): List<RateLocalModel> {
        return emptyList()
    }

    private fun createDummyRateLocalModelList(): List<RateLocalModel> {
        val arrayListOf = arrayListOf<RateLocalModel>()
        arrayListOf.add(RateLocalModel(Currency.getInstance("TRY"), BigDecimal(1.6202)))
        arrayListOf.add(RateLocalModel(Currency.getInstance("BGN"), BigDecimal(4.6202)))
        arrayListOf.add(RateLocalModel(Currency.getInstance("BRL"), BigDecimal(7.6202)))
        arrayListOf.add(RateLocalModel(Currency.getInstance("CAD"), BigDecimal(2.6202)))


        return arrayListOf
    }
}