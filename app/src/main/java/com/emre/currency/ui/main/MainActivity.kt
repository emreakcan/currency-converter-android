package com.emre.currency.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.emre.currency.R
import com.emre.currency.databinding.ActivityMainBinding
import com.emre.currency.ui.adapter.RatesAdapter
import com.emre.currency.ui.base.BaseActivity
import com.emre.currency.util.extension.observeNotNull
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    override val getLayoutId = R.layout.activity_main
    override val viewModelClass: Class<MainViewModel> = MainViewModel::class.java

    private val listAdapter by lazy {
        return@lazy RatesAdapter(
            this,
            viewModel::onCurrencyAmountChanged,
            viewModel::onCalculateExchangeRateListsDifferences,
            mutableListOf(),
            viewModel::onCurrencyItemSelected
            ).apply { setHasStableIds(true) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.observeLastSelectedCurrency()

        with(list_rates) {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }

        viewModel.currenciesLD.observeNotNull(this) {
            listAdapter.addData(it)
        }

        viewModel.errorMessage.observeNotNull(this) {
            Toast.makeText(this, it.originalException?.message, Toast.LENGTH_SHORT).show()
        }
    }
}
