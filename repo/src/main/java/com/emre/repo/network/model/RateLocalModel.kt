package com.emre.repo.network.model

import java.math.BigDecimal
import java.util.*

data class RateLocalModel(
    val currency: Currency,
    val amount: BigDecimal
)