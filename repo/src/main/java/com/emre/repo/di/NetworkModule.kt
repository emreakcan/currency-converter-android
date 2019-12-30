package com.emre.repo.di

import com.emre.common.Constants
import com.emre.common.di.scope.RepositoryScope
import com.emre.repo.network.RetrofitApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Module
class NetworkModule {

    @RepositoryScope
    @Provides
    fun provideGson(): Gson = Gson()

    @RepositoryScope
    @Provides
    fun provideGtcApi(okHttpClient: OkHttpClient, gson: Gson): RetrofitApi {
        return Retrofit.Builder()
            .baseUrl(Constants.SERVER_URL)
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(RetrofitApi::class.java)
    }

    @RepositoryScope
    @Provides
    fun provideOkHttp(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .readTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .connectTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(Constants.NETWORK_TIME_OUT, TimeUnit.SECONDS)
            .build()
    }

    @RepositoryScope
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }
}