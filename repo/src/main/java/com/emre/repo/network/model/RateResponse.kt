package com.emre.repo.network.model

import com.google.gson.annotations.SerializedName

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */

data class RateResponse(
    @field:SerializedName("base") val baseCurrency: String,
    @field:SerializedName("rates") val ratesMap: Map<String, String>
)