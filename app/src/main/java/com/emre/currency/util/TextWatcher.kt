package com.emre.currency.util

import android.text.Editable
import android.text.TextWatcher
import java.math.BigDecimal


class TextWatcher(private val onAmountChanged: (BigDecimal) -> Unit) :
    TextWatcher {
    override fun afterTextChanged(p0: Editable?) {}

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(currentValue: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (currentValue.isNullOrBlank())
            return

        onAmountChanged(BigDecimal(currentValue.toString()))
    }
}