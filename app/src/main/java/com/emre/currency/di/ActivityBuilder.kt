package com.emre.currency.di

import com.emre.currency.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    internal abstract fun bindMainActivity(): MainActivity

}