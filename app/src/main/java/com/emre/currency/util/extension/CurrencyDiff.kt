package com.emre.currency.util.extension

import androidx.recyclerview.widget.DiffUtil
import com.emre.repo.network.model.RateLocalModel
import java.math.BigDecimal

class CurrencyDiff(
    private val oldList: List<RateLocalModel>,
    private val newList: List<RateLocalModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].currency.currencyCode == newList[newItemPosition].currency.currencyCode

    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldList[oldItemPosition].amount == newList[newItemPosition].amount

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? =
        CurrencyAmountDifference(newList[newItemPosition].amount)
}

data class CurrencyAmountDifference(val newAmount: BigDecimal)