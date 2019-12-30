package com.emre.repo.di

import android.content.Context
import com.emre.common.di.scope.RepositoryScope
import com.emre.repo.repository.currency.CurrencyLocalRepository
import com.emre.repo.repository.currency.CurrencyRepository
import dagger.BindsInstance
import dagger.Component

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@RepositoryScope
@Component(
    modules = [
        RepositoryModule::class
    ]
)
interface RepositoryComponent {
    fun getCurrencyRepository(): CurrencyRepository
    fun getCurrencyLocalRepositoryImpl(): CurrencyLocalRepository

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Context): Builder

        fun build(): RepositoryComponent
    }
}