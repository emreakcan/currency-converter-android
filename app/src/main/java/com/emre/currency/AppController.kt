package com.emre.currency

import com.emre.currency.di.AppComponent
import com.emre.currency.di.DaggerAppComponent
import com.emre.repo.di.DaggerRepositoryComponent
import com.emre.repo.di.RepositoryComponent
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import java.math.BigDecimal
import java.util.*

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

class AppController : DaggerApplication() {

    private val repositoryComponent: RepositoryComponent by lazy {
        DaggerRepositoryComponent.builder()
            .context(applicationContext)
            .build()
    }

    private val appComponent: AppComponent by lazy {
        DaggerAppComponent.builder()
            .application(this)
            .setRepositoryComponent(repositoryComponent)
            .build()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }

    override fun onCreate() {
        super.onCreate()

        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}