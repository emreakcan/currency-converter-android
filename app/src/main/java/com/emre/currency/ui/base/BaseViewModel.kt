package com.emre.currency.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emre.common.model.BaseException
import io.reactivex.disposables.CompositeDisposable

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
abstract class BaseViewModel : ViewModel() {
    val compositeDisposable = CompositeDisposable()
    val isDataLoading = MutableLiveData<Boolean>()
    val errorMessage = MutableLiveData<BaseException>()

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

}