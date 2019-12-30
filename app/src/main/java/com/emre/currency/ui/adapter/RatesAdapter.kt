package com.emre.currency.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.emre.common.Constants
import com.emre.currency.R
import com.emre.currency.util.TextWatcher
import com.emre.currency.util.extension.CurrencyAmountDifference
import com.emre.repo.network.model.RateLocalModel
import com.squareup.picasso.Picasso
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.item_currency.view.*
import java.math.BigDecimal
import java.util.*


class RatesAdapter(
    private val context: Context,
    onCurrencyAmountChanged: (BigDecimal) -> Unit,
    private val diffList: (List<RateLocalModel>, List<RateLocalModel>) -> Single<DiffUtil.DiffResult>,
    private var ratesList: MutableList<RateLocalModel>,
    private val onCellClicked: (RateLocalModel) -> Unit
) : RecyclerView.Adapter<RatesAdapter.CurrencyItemViewHolder>() {

    private var calculationDisposable: Disposable? = null

    private val textWatcher = TextWatcher(onCurrencyAmountChanged)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyItemViewHolder {
        val viewHolder = CurrencyItemViewHolder(LayoutInflater.from(context).inflate(R.layout.item_currency, parent, false))

        viewHolder.itemView.setOnClickListener {
            if (viewHolder.adapterPosition != 0) viewHolder.edtCurAmount.requestFocus()
        }

        viewHolder.edtCurAmount.apply {
            setOnFocusChangeListener { _, focused ->
                when (focused) {
                    true -> {
                        addTextChangedListener(textWatcher)
                        onCellClicked(ratesList[viewHolder.adapterPosition])
                    }
                    false -> removeTextChangedListener(textWatcher)
                }
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: CurrencyItemViewHolder, position: Int) {
        val currencyItem = ratesList[position]
        holder.bind(currencyItem)
    }

    override fun onBindViewHolder(
        holder: CurrencyItemViewHolder,
        position: Int,
        payloadList: MutableList<Any>
    ) {

        if(payloadList.isNotEmpty()){
            with(holder.edtCurAmount) {
                if (!isFocused) setText(
                    (payloadList[0] as CurrencyAmountDifference).newAmount
                        .setScale(Constants.MAX_FLOATING_POINTS, BigDecimal.ROUND_HALF_EVEN).toPlainString()
                )
            }
        }else{
            onBindViewHolder(holder, position)
        }

    }

    override fun getItemCount(): Int {
        return ratesList.size
    }

    fun addData(newRateLocalModel: List<RateLocalModel>) {
        diffList(ratesList, newRateLocalModel)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                ratesList.clear()
                ratesList.addAll(newRateLocalModel)
                it.dispatchUpdatesTo(this@RatesAdapter)
            }, {}).let { calculationDisposable = it }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        calculationDisposable?.dispose()
    }

    override fun getItemId(position: Int): Long =
        ratesList[position].currency.hashCode().toLong()

    class CurrencyItemViewHolder(itemLayout: View) : RecyclerView.ViewHolder(itemLayout) {
        val edtCurAmount: EditText = itemLayout.edt_amount
        private val context = itemView.context

        fun bind(currencyItem: RateLocalModel) {
            itemView.txt_cur_code.text = currencyItem.currency.currencyCode
            itemView.txt_cur_name.text = currencyItem.currency.displayName
            Picasso.get()
                .load(
                    context.resources.getIdentifier(
                        context.resources.getString(R.string.flag_) + currencyItem.currency.currencyCode.toLowerCase(Locale.US),
                        context.resources.getString(R.string.drawable),
                        context.applicationInfo.packageName
                    )
                )
                .into(itemView.img_currency)

            if (!itemView.edt_amount.isFocused) {
                itemView.edt_amount.setText(currencyItem.amount.setScale(Constants.MAX_FLOATING_POINTS, BigDecimal.ROUND_HALF_EVEN).toPlainString())
            }
        }
    }
}