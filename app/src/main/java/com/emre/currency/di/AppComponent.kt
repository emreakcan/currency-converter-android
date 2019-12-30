package com.emre.currency.di

import android.app.Application
import com.emre.common.di.scope.AppScope
import com.emre.currency.AppController
import com.emre.currency.di.module.AppModule
import com.emre.currency.di.module.UseCaseModule
import com.emre.currency.di.module.ViewModelModule
import com.emre.repo.di.RepositoryComponent
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

@AppScope
@Component(
    dependencies = [
        RepositoryComponent::class
    ],
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        AppModule::class,
        ActivityBuilder::class,
        UseCaseModule::class
    ]
)
interface AppComponent : AndroidInjector<AppController> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun setRepositoryComponent(repositoryComponent: RepositoryComponent): Builder

        fun build(): AppComponent
    }

    override fun inject(app: AppController?)
}