package com.example.crfpos.model.selected

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
data class PendingPurchase(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val name: String,
    val unitPrice: Int,
    val numOfOrder: Int,
    val amount: Int
) : Parcelable