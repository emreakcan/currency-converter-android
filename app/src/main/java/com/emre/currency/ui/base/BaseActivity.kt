package com.emre.currency.ui.base

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.emre.currency.BR
import com.emre.currency.ui.LoadingDialog
import com.emre.currency.util.extension.observeNotNull
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : DaggerAppCompatActivity() {
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: VM
    protected lateinit var binding: DB
    abstract val getLayoutId: Int
    abstract val viewModelClass: Class<VM>

    private val loadingDialog: LoadingDialog by lazy {
        LoadingDialog(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        bind()
        subscribeLoading()
    }

    private fun bind() {
        binding = DataBindingUtil.setContentView(this, getLayoutId)
        binding.setVariable(BR.viewModel, viewModel)
        binding.lifecycleOwner = this
    }

    protected open fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(viewModelClass)
    }

    private fun subscribeLoading() {
        viewModel.isDataLoading.observeNotNull(this) {
            when (it) {
                true -> loadingDialog.showLoading()
                false -> loadingDialog.hideLoading()
            }
        }
    }
}