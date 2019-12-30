package com.emre.currency.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emre.common.di.scope.AppScope
import com.emre.currency.di.ViewModelFactory
import com.emre.currency.di.ViewModelKey
import com.emre.currency.ui.main.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Module(includes = [UseCaseModule::class])
abstract class ViewModelModule {
    @AppScope
    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory


    @AppScope
    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun MessageDialogViewModel(viewModel: MainViewModel): ViewModel
}