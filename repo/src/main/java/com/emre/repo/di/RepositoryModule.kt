package com.emre.repo.di

import com.emre.common.di.scope.RepositoryScope
import com.emre.repo.repository.currency.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

@Module(
    includes = [
        NetworkModule::class,
        DatabaseModule::class
    ]
)
abstract class RepositoryModule {
    @RepositoryScope
    @Binds
    abstract fun bindCurrencyRepository(repositoryImpl: CurrencyRepositoryImpl): CurrencyRepository

    @RepositoryScope
    @Binds
    abstract fun bindCurrencyLocalRepository(repositoryImpl: CurrencyLocalRepositoryImpl): CurrencyLocalRepository
}