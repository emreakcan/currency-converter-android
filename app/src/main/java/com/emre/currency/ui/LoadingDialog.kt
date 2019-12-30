package com.emre.currency.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import com.emre.currency.R

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
class LoadingDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        if (window != null)
            window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        setContentView(R.layout.loading_dialog)
        setCanceledOnTouchOutside(false)
        setCancelable(false)
    }

    fun showLoading() {
        if (!isShowing) {
            show()
        }
    }

    fun hideLoading() {
        if (isShowing) {
            dismiss()
        }
    }

}