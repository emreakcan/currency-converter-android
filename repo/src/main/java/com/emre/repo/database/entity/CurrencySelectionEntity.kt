package com.emre.repo.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created on 2019-12-28.
 * @author EMRE AKCAN
 */
@Entity
data class CurrencySelectionEntity(
    @PrimaryKey
    val code: String,
    val amount: String)