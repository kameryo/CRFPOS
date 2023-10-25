package com.example.crfpos.model.record

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.crfpos.model.selected.PendingPurchase
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Record(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val time: Long,
    val total: Int,
    val fareSales: Int,
    val couponSales: Int,
    val goodsSales: Int,
    val adult: Int,
    val child: Int,
    val couponList: List<PendingPurchase>?,
    val goodsList: List<PendingPurchase>?,
    val memo: String
) : Parcelable