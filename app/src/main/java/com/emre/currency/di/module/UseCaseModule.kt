package com.emre.currency.di.module

import com.emre.common.di.scope.AppScope
import com.emre.currency.usecase.GetCurrenciesUseCase
import com.emre.currency.usecase.GetLastCurrencyUseCase
import com.emre.currency.usecase.UpdateCurrencyCodeUseCase
import com.emre.repo.repository.currency.CurrencyLocalRepository
import com.emre.repo.repository.currency.CurrencyRepository
import dagger.Module
import dagger.Provides

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Module
class UseCaseModule {
    @AppScope
    @Provides
    fun providesCurrenciesUseCase(currencyRepository: CurrencyRepository): GetCurrenciesUseCase {
        return GetCurrenciesUseCase(currencyRepository)
    }


    @AppScope
    @Provides
    fun providesChangeBaseCurrencyUseCase(repository: CurrencyLocalRepository): UpdateCurrencyCodeUseCase {
        return UpdateCurrencyCodeUseCase(repository)
    }

    @AppScope
    @Provides
    fun providesGetLastCurrencyUseCase(repository: CurrencyLocalRepository): GetLastCurrencyUseCase {
        return GetLastCurrencyUseCase(repository)
    }

}